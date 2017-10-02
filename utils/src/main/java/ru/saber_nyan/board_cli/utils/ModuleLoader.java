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

package ru.saber_nyan.board_cli.utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class for loading modules from .jar files.
 */
public final class ModuleLoader {

//	private static final Logger logger = (Logger) LoggerFactory.getLogger(ModuleLoader.class);

	/**
	 * Load {@link Class} from specified .jar file.
	 *
	 * @param path               .jar-file path
	 * @param loadIfNameContains load only matching classes
	 * @return loaded classes list
	 * @throws IOException            if an I/O error has occurred
	 * @throws ClassNotFoundException (will never happen) if wrong class name selected
	 */
	public static ArrayList<Class> loadClassesFromJar(@NotNull String path,
													  @NotNull String loadIfNameContains)
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

			String className = jarEntry.getName().substring(0,
					jarEntry.getName().length() - ".class".length());
			className = className.replace('/', '.');
//			logger.trace("found class \"{}\"", className);
			if (!className.contains(loadIfNameContains)
					|| className.startsWith("ru.saber_nyan.board_cli.module.ModuleInfo")) {
//				logger.trace("skipping class");
				continue;
			}
			classes.add(classLoader.loadClass(className));
//			logger.debug("loaded {}!", className);
		}
		return classes;
	}
}
