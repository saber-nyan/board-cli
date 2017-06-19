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
