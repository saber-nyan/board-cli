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

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.saber_nyan.board_cli.core.Module;
import ru.saber_nyan.board_cli.module.ImageboardBoard;

import static ru.saber_nyan.board_cli.core.Main.TMP_DIR;
import static ru.saber_nyan.board_cli.core.Main.VAR_NAME_TMP_DIR;

/**
 * Threads list GUI.
 * <p>
 * When thread is selected, displays TODO.
 */
public class ThreadsScreen {

	private static final Logger logger;

	/**
	 * Selected board (threads not loaded, need to
	 * invoke {@link ImageboardBoard#load()}) explicitly.
	 */
	@NotNull
	private final ImageboardBoard board;

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
	 * @param board        {@link #board}
	 * @param gui          {@link #gui}
	 * @param module       {@link #module}
	 * @param okHttpClient {@link #okHttpClient}
	 */
	public ThreadsScreen(@NotNull ImageboardBoard board,
						 @NotNull MultiWindowTextGUI gui,
						 @NotNull Module module,
						 @NotNull OkHttpClient okHttpClient) {
		super();
		this.board = board;
		this.gui = gui;
		this.module = module;
		this.okHttpClient = okHttpClient;
	}

	public void draw() {

	}
}
