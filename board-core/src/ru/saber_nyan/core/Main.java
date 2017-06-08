package ru.saber_nyan.core;

import ru.saber_nyan.parser.Board;
import ru.saber_nyan.parser.Thread;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private static final PrintStream err = System.err;
	private static final PrintStream out = System.out;
	private static final Scanner in = new Scanner(System.in);
	private static final String JAR_NAME = "board-core.jar";
	private static final String FIELD_MODULE_NAME = "moduleName";

	public static void main(String[] args) {
		try {
			Module module = loadModules();
			Board board = (Board) module.getBoard()
					.getDeclaredConstructor(String.class, String.class, long.class)
					.newInstance("Linux", "s", 0);
			board.load();
			ArrayList<Thread> threads = board.getThreads();
			for (Thread thread : threads) {
				thread.load();
				out.println("№" + thread.getNumber() + " \"" + thread.getTitle() + "\" [" + thread.getPostCount() + "]\n##########\n" +
						thread.getPosts().get(0).getComment() +
						"\n-=-=-=-=-=-=-=-=-=-=-=-=-=-\n\n");
			}
			out.println("Long name: " + board.getLongName() +
					"\nBumplimit: " + board.getBumpLimit());

		} catch (Exception e) {
			e.printStackTrace(err);
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

		for (File file : filesInJarDir) {
			String name = file.getName();
			if (file.isFile() && name.endsWith(".jar") && !name.equals(JAR_NAME)) {

				out.println("Got jar \"" + name + "\"!");

				module = new Module(file.getAbsolutePath());
				ArrayList<Class> classes = module.getClasses();

				module.loadAllModules(classes.get(0)); // TODO: Выбор модуля из GUI

				for (Class loadedClass : classes) {
					out.println("Loaded class \"" + loadedClass.getCanonicalName() + "\"");
					Field nameField = loadedClass.getField(FIELD_MODULE_NAME);
					String nameStr = (String) nameField.get(loadedClass);
					out.println("\tname \"" + nameStr + "\"");
				}
			}
		}
		return module;
	}
}
