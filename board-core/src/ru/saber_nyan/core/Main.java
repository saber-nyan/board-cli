package ru.saber_nyan.core;

import ru.saber_nyan.utils.ClassLoader;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private final static PrintStream err = System.err;
	private final static PrintStream out = System.out;
	private final static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		out.print("Select JAR path:\n> ");
		String path = in.nextLine();
		try {
			ClassLoader loader = new ClassLoader();
			ArrayList<Class> classes = loader.loadClassFromJar(path, new String[]{"Board", "Thread", "Post", "File"},
					"module");
			for (Class aClass : classes) {
				out.println("Loaded class \"" + aClass.getCanonicalName() + "\"!");
			}
		} catch (IOException | ClassNotFoundException e) {
			err.println("Error while loading class:");
			e.printStackTrace(err);
		}
	}
}
