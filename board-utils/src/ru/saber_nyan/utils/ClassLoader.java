package ru.saber_nyan.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLoader {
	public ArrayList<Class> loadClassesFromJar(String path, String loadIfContains)
			throws IOException, ClassNotFoundException {

		JarFile jarFile = new JarFile(path);
		Enumeration<JarEntry> entryEnumeration = jarFile.entries();

		URL[] urls = {
				new URL("jar:file:" + path + "!/")
		};
		URLClassLoader classLoader = URLClassLoader.newInstance(urls);

		ArrayList<Class> classes = new ArrayList<>();

		while (entryEnumeration.hasMoreElements()) {
			JarEntry jarEntry = entryEnumeration.nextElement();
			if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
				continue;
			}

			String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6); // Расширение .class
			className = className.replace('/', '.');

			if (!className.contains(loadIfContains) || className.contains("ru.saber_nyan.parser")) { // Не грузим абстрактный класс
				continue;
			}

			classes.add(classLoader.loadClass(className));
		}
		return classes;
	}
}
