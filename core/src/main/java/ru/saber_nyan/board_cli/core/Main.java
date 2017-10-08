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

package ru.saber_nyan.board_cli.core;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.saber_nyan.board_cli.core.gui.BoardsScreen;
import ru.saber_nyan.board_cli.module.ImageboardBoard;
import ru.saber_nyan.board_cli.module.ImageboardImageboard;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Here we go!
 */
public final class Main {

	/**
	 * System temporary directory. We hope it's r/w...
	 */
	public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
	/**
	 * Logback path variable name.
	 */
	public static final String VAR_NAME_TMP_DIR = "bcli_logback_log_path";

	private static final Logger logger;

	private static final String OPTION_HELP = "h";
	private static final String OPTION_USERAGENT = "ua";
	private static final String OPTION_CONNECT_TIMEOUT = "ct";
	private static final String OPTION_READWRITE_TIMEOUT = "rwt";

	private static final String DEFAULT_USER_AGENT
			= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092816 Mobile Safari 1.1.3";

	private static final int EXITCODE_UNKNOWN = -256;
	private static final int EXITCODE_MODULE_LOAD = -2;
	private static final int EXITCODE_OPTIONS = -4;
	private static final int EXITCODE_TERMINAL = -16;
	private static final int EXITCODE_NO_BOARDS = -32;
	private static final int EXITCODE_MODULE_INIT = -64;
	private static final String FIELD_MODULE_NAME = "moduleName";

	private static final String JAR_NAME = "board-core.jar";

	private static MultiWindowTextGUI gui;

	static {
		System.setProperty(VAR_NAME_TMP_DIR, TMP_DIR);
		logger = LoggerFactory.getLogger(Main.class);
	}

	private static void die(Throwable e) {
		logger.error("Shit, unknown exception was thrown!\n" +
				"Please report the bug (with stacktrace) to https://github.com/saber-nyan/board-cli/issues\n" +
				"Logs are stored here: " + TMP_DIR + "\n" +
				"Stacktrace:", e);
		System.exit(EXITCODE_UNKNOWN);
	}

	/**
	 * Catches and reports any exception.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		logger.debug("---NEW LAUNCH---");
		try {
			Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> die(exception));
			System.exit(main0(args));
		} catch (Throwable e) {
			die(e);
		}
	}

	/**
	 * We log every exception!
	 */
	private static int main0(String[] args) throws Throwable {
		// ARG PARSE
		Options options = new Options();
		options.addOption(Option.builder(OPTION_HELP)
				.longOpt("help")
				.desc("display help and exit")
				.build());
		options.addOption(Option.builder(OPTION_USERAGENT)
				.longOpt("user-agent")
				.desc("User-Agent used for posting")
				.hasArg()
				.argName("User-Agent")
				.type(String.class)
				.build());
		options.addOption(Option.builder(OPTION_CONNECT_TIMEOUT)
				.longOpt("connect-timeout")
				.desc("connection timeout for HTTP client (seconds)")
				.hasArg()
				.argName("timeout")
				.type(Integer.class)
				.build());
		options.addOption(Option.builder(OPTION_READWRITE_TIMEOUT)
				.longOpt("read-write-timeout")
				.desc("read/write timeout for HTTP client (seconds)")
				.hasArg()
				.argName("timeout")
				.type(Integer.class)
				.build());

		CommandLineParser cmdParser = new DefaultParser();
		CommandLine cmdArgs;
		try {
			cmdArgs = cmdParser.parse(options, args);
		} catch (ParseException e) {
			logger.error("{}", e.getMessage());
			return EXITCODE_OPTIONS;
		}

		if (cmdArgs.hasOption(OPTION_HELP)) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp(
					JAR_NAME,
					"Kawaii modular imageboard client~\n\n",
					options,
					"\nLogs path: " + TMP_DIR + "\n" +
							"Licensed under the Apache License, Version 2.0\n" +
							"https://github.com/saber-nyan/board-cli",
					true
			);
			return 0;
		}

		String userAgent = cmdArgs.getOptionValue(OPTION_USERAGENT, DEFAULT_USER_AGENT);
		int connectTimeout;
		int rwTimeout;
		try {
			connectTimeout = Integer.parseInt(cmdArgs.getOptionValue(OPTION_CONNECT_TIMEOUT, "5"));
			rwTimeout = Integer.parseInt(cmdArgs.getOptionValue(OPTION_READWRITE_TIMEOUT, "6"));
		} catch (NumberFormatException e) {
			logger.error("Incorrect timeout!");
			return EXITCODE_OPTIONS;
		}
		if (connectTimeout <= 0 || rwTimeout <= 0) {
			logger.error("Timeout must be > 0!");
			return EXITCODE_OPTIONS;
		}

		// END ARG PARSE

		logger.trace("rw timeout = {}", rwTimeout);

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(connectTimeout, TimeUnit.SECONDS)
				.readTimeout(rwTimeout, TimeUnit.SECONDS)
				.writeTimeout(rwTimeout, TimeUnit.SECONDS)
				.addNetworkInterceptor(chain -> {
					Request original = chain.request();
					Request requestWithUA = original.newBuilder()
							.header("User-Agent", userAgent)
							.build();
					return chain.proceed(requestWithUA);
				})
				.build();

		// TERMINAL INIT
		Screen screen;
		try {
			Terminal terminal = new DefaultTerminalFactory(System.out, System.in, Charset.forName("UTF-8"))
					.createTerminal();
			screen = new TerminalScreen(terminal);
			screen.startScreen();
			logger.debug("term init success");
		} catch (Exception e) {
			logger.error("Terminal initialization failed!", e);
			return EXITCODE_TERMINAL;
		}
		// END TERMINAL INIT

		// MODULE SELECT + INIT
		Module module;
		try {
			module = loadModule(screen);
		} catch (Exception e) {
			logger.error("Module loading failed!", e);
			return EXITCODE_MODULE_LOAD;
		}


		ImageboardImageboard imageboard = (ImageboardImageboard)
				module.getImageboard().newInstance(okHttpClient);
		// At this point we got boards list, so display it!

		List<ImageboardBoard> boards = imageboard.getBoards();
		if (boards == null) {
			logger.error("boards list is null...");
			return EXITCODE_NO_BOARDS;
		}

		BoardsScreen boardsScreen = new BoardsScreen(boards, gui,
				module, okHttpClient);
		try {
			boardsScreen.draw();
		} catch (Throwable e) {
			logger.error("Module init fail", e);
			return EXITCODE_MODULE_INIT;
		}

		return 0; // TODO: Loop?
	}

	@NotNull
	private static Module loadModule(Screen screen) throws URISyntaxException, IOException,
			ClassNotFoundException, NoSuchFieldException, IllegalAccessException,
			RuntimeException, NoSuchMethodException {
		File jarFile = new File(Main.class.getProtectionDomain()
				.getCodeSource().getLocation().toURI().getPath());
		File jarDir = jarFile.getParentFile();
		logger.trace("jarPath is {}", jarDir);

		File[] filesInJarDir = jarDir.listFiles();

		Module module = null;
		ArrayList<Class> classes = null;

		// GUI element:
		RadioBoxList<String> modulesRadioBoxList = new RadioBoxList<>();

		if (filesInJarDir == null) {
			logger.warn("No modules found!");
			throw new IllegalStateException("No modules found!");
		}

		for (File file : filesInJarDir) { // Searching jars...
			String filename = file.getName();
			if (file.isFile() && !JAR_NAME.equals(filename) && filename.endsWith(".jar")) {
				logger.trace("got jar: {}", filename);
				module = new Module(file.getAbsolutePath());
				classes = module.getClasses();
				classes.forEach(loadedClass -> {
					logger.debug("loaded class \"{}\"", loadedClass.getCanonicalName());
					Field nameField;
					String nameStr = "";
					try {
						nameField = loadedClass.getField(FIELD_MODULE_NAME);
						nameField.setAccessible(true);
						nameStr = (String) nameField.get(loadedClass);

						modulesRadioBoxList.addItem(nameStr);
					} catch (NoSuchFieldException | IllegalAccessException e) {
						logger.error("can't get class name!", e);
					}
					logger.debug("with name \"{}\"", nameStr);
				});
			}
		}
		modulesRadioBoxList.setCheckedItemIndex(0);

		gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace());
//		gui.setTheme(SimpleTheme.makeTheme()); // TODO: customization
		gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT));

		Window window = new BasicWindow("Select module");
		window.setHints(Collections.singletonList(Window.Hint.CENTERED));

		Panel panel = new Panel(new LinearLayout().setSpacing(1));
		panel.addComponent(modulesRadioBoxList);
		panel.addComponent(new Button("OK", window::close)
				.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center))
		);
		window.setComponent(panel);
		gui.addWindowAndWait(window);

		// Wait until user click "OK"...

		window.close();
		int chosenItem = modulesRadioBoxList.getCheckedItemIndex();


		if (module == null) {
			throw new RuntimeException("no modules found!");
		}

		module.loadAllModules(classes.get(chosenItem));
		return module;
	}

}
