/*******************************************************************************
 * Copyright (c) 2017 saber-nyan
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 ******************************************************************************/

package ru.saber_nyan.core;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import ru.saber_nyan.module.abstraction.Board;
import ru.saber_nyan.module.abstraction.Imageboard;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("unchecked")
class Main {

	private static final PrintStream err = System.err; // Текст не отображается в терминале...
	private static final PrintStream out = System.out;
	private static final String JAR_NAME = "board-core.jar";
	private static final String FIELD_MODULE_NAME = "moduleName";
	private static final int EXITCODE_ERR_TERMINAL_CLOSE = 1;
	private static final int EXITCODE_ERR_TERMINAL_INIT = 2;
	private static final int EXITCODE_ERR_MODULE_LOAD = 3;
	private static final int EXITCODE_ERR_DATA_DOWNLOAD = 4;
	private static Terminal terminal;

	public static void main(String[] args) {

		Module module = null;
		try {
			terminal = new DefaultTerminalFactory().createTerminal();
		} catch (IOException e) {
			err.print("Error initializing the terminal: ");
			e.printStackTrace();
			System.exit(EXITCODE_ERR_TERMINAL_INIT);
		}

		try {
			module = loadModules();
		} catch (Exception e) {
			err.print("Error while module loading: ");
			e.printStackTrace(err);
			System.exit(EXITCODE_ERR_MODULE_LOAD);
		}

		try {
			Imageboard imageboard = (Imageboard) module.getImageboard()
					.getDeclaredConstructor(String.class)
					.newInstance("Linux");

			ArrayList<Board> boards = imageboard.getBoards();
			boards.forEach(board -> out.println("Got board \"" + board.getLongName()
					+ "\" (" + board.getName() + ")"));
		} catch (Exception e) {
			err.print("Error while downloading data: ");
			e.printStackTrace(err);
			System.exit(EXITCODE_ERR_DATA_DOWNLOAD);
		}
		try {
			terminal.close();
		} catch (IOException e) {
			err.print("Error closing the terminal: ");
			e.printStackTrace();
			System.exit(EXITCODE_ERR_TERMINAL_CLOSE);
		}
	}

	private static Module loadModules() throws Exception {
		File jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()
				.replace(JAR_NAME, ""));

		String jarPath = jarDir.getAbsolutePath();
		out.println("JAR path: " + jarPath);

		// List directory
		File[] filesInJarDir = jarDir.listFiles();
		assert filesInJarDir != null; // Есть как минимум один файл - board-core.jar

		Module module = null;
		RadioBoxList<String> radioBoxList = new RadioBoxList<>();

		ArrayList<Class> classes = null;

		try {
			for (File file : filesInJarDir) {
				String name = file.getName();
				if (file.isFile() && name.endsWith(".jar") && !name.equals(JAR_NAME)) {

					out.println("Got jar \"" + name + "\"!");

					module = new Module(file.getAbsolutePath());
					classes = module.getClasses();

					for (Class loadedClass : classes) {
						out.println("Loaded class \"" + loadedClass.getCanonicalName() + "\"");
						Field nameField = loadedClass.getField(FIELD_MODULE_NAME);
						String nameStr = (String) nameField.get(loadedClass);
						out.println("\tname \"" + nameStr + "\"");

						radioBoxList.addItem(nameStr);
					}
				}
			}
		} catch (NullPointerException e) {
			throw new RuntimeException("No modules found!", e);
		}

		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();
		BasicWindow window = new BasicWindow("Выберите модуль");
		window.setHints(Collections.singletonList(Window.Hint.CENTERED));

		Panel panel = new Panel(new LinearLayout().setSpacing(1));
		radioBoxList.setCheckedItemIndex(0);
		panel.addComponent(radioBoxList);
		panel.addComponent(new Button("OK", window::close).setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));
		window.setComponent(panel);

		MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace());
		gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT));
		gui.addWindowAndWait(window);

		int chosenItem = radioBoxList.getCheckedItemIndex();

		try {
			assert classes != null;
			out.println("Classes: " + classes.toString());
			out.println("Chosen " + String.valueOf(chosenItem) + "!");
			module.loadAllModules(classes.get(chosenItem));
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException("Attempt to load an incorrect module!", e);
		} catch (NullPointerException e) {
			throw new RuntimeException("Modules not found!", e);
		}
		return module;
	}
}
