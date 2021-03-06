/******************************************************************************
 * Copyright © 2017 saber-nyan                                                *
 *                                                                            *
 * Licensed under the Apache License, Version 2.0 (the "License");            *
 * you may not use this file except in compliance with the License.           *
 * You may obtain a copy of the License at                                    *
 *                                                                            *
 *     http://www.apache.org/licenses/LICENSE-2.0                             *
 *                                                                            *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 ******************************************************************************/

package ru.saber_nyan.board_cli.core.gui;

import com.googlecode.lanterna.gui2.*;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.saber_nyan.board_cli.core.Module;
import ru.saber_nyan.board_cli.module.ImageboardBoard;
import ru.saber_nyan.board_cli.utils.HttpException;
import ru.saber_nyan.board_cli.utils.Pair;
import ru.saber_nyan.board_cli.utils.Utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static ru.saber_nyan.board_cli.core.Main.TMP_DIR;
import static ru.saber_nyan.board_cli.core.Main.VAR_NAME_TMP_DIR;

/**
 * Boards list GUI.
 * <p>
 * When board is selected, displays {@link ThreadsScreen}.
 */
public class BoardsScreen {

	private static final Logger logger;

	/**
	 * List of boards.
	 */
	@NotNull
	private final List<ImageboardBoard> boardList;

	/**
	 * The console GUI.
	 */
	@NotNull
	private final MultiWindowTextGUI gui;

	/**
	 * {@link Module} with loaded classes.
	 */
	@NotNull
	private final Module module;

	/**
	 * HTTP client.
	 */
	@NotNull
	private final OkHttpClient okHttpClient;

	static {
		System.setProperty(VAR_NAME_TMP_DIR, TMP_DIR);
		logger = LoggerFactory.getLogger(BoardsScreen.class);
	}

	/**
	 * @param boardList    {@link #boardList}
	 * @param gui          {@link #gui}
	 * @param module       {@link #module}
	 * @param okHttpClient {@link #okHttpClient}
	 */
	public BoardsScreen(@NotNull List<ImageboardBoard> boardList,
						@NotNull MultiWindowTextGUI gui,
						@NotNull Module module,
						@NotNull OkHttpClient okHttpClient) {
		super();
		this.boardList = boardList;
		this.gui = gui;
		this.module = module;
		this.okHttpClient = okHttpClient;
	}

	/**
	 * Displays board selection dialog.
	 */
	public void draw() {
		Window window = new BasicWindow("Select board");
		window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN, Window.Hint.NO_DECORATIONS));

		Panel basePanel = new Panel(new LinearLayout().setSpacing(1));

		Panel customBoardNamePanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
		TextBox customBoardNameTextBox = new TextBox()
				.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));

		Button okButton = new Button("OK", window::close)
				.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
		Button exitButton = new Button("Exit", () -> System.exit(0))
				.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
		customBoardNamePanel.addComponent(customBoardNameTextBox
				.withBorder(Borders.singleLine("Board name")));
		customBoardNamePanel.addComponent(okButton);
		customBoardNamePanel.addComponent(exitButton);

		basePanel.addComponent(customBoardNamePanel);

		RadioBoxList<Pair<String, String>> boardsRadioBoxList = new RadioBoxList<>(gui.getScreen().getTerminalSize());

		boardList.forEach(board ->
				boardsRadioBoxList.addItem(new Pair<>("/%s/ \u2015 %s",
						board.getAbbreviation(),
						board.getTitle())));

		basePanel.addComponent(boardsRadioBoxList);

		window.setComponent(basePanel);
		gui.addWindowAndWait(window);

		// Wait fo "OK"...

		window.close();
		Pair<String, String> chosen = boardsRadioBoxList.getCheckedItem();
		if (!customBoardNameTextBox.getText().isEmpty()) {
			chosen = new Pair<>("%s%s", customBoardNameTextBox.getText(),
					null);
		}

		load(chosen);
	}

	/**
	 * Load selected board.
	 *
	 * @param selectedItem selected item pair
	 */
	private void load(@NotNull Pair<String, String> selectedItem) {
		String boardAbbr = trimAbbr(selectedItem.getFirst());
		String boardName = selectedItem.getSecond();
		logger.debug("Trying to load \"{}\"...", boardAbbr);

		ImageboardBoard board;
		try {
			board = (ImageboardBoard) module.getBoard()
					.newInstance(boardAbbr, 0L, boardName, okHttpClient);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("while loading selected board:", e); // Will never happen~
		}

		try {
			board.load();
			logger.debug("board loaded");
		} catch (HttpException e) {
			Utils.reportError(logger, gui,
					"Server responded \"" + e.getResponseCode() + "\"", e);
		} catch (IOException e) {
			Utils.reportError(logger, gui,
					"Request failed: " + e.getLocalizedMessage(), e);
		} catch (JSONException e) {
			Utils.reportError(logger, gui,
					"Server responded with invalid JSON: " + e.getLocalizedMessage(), e);
		}

		ThreadsScreen threadsScreen = new ThreadsScreen(board, gui, module, okHttpClient);
		threadsScreen.draw();

		// When user exits from ThreadSelectScreen, show Board selection
		draw();
	}

	/**
	 * Deletes leading and trailing slashes.
	 *
	 * @param boardAbbr input string
	 * @return string w/o slashes
	 */
	@NotNull
	private static String trimAbbr(String boardAbbr) {
		if (!boardAbbr.isEmpty() && boardAbbr.startsWith("/")) {
			logger.trace("starts with \'/\', trimming");
			boardAbbr = boardAbbr.substring(1);
		}
		if (!boardAbbr.isEmpty() && boardAbbr.endsWith("/")) {
			logger.trace("ends with \'/\', trimming");
			boardAbbr = boardAbbr.substring(0, boardAbbr.length() - 1);
		}
		return boardAbbr;
	}
}
