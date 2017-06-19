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

import ru.saber_nyan.parser.ModuleInfo;
import ru.saber_nyan.utils.ClassLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

@SuppressWarnings("unused")
class Module {

	private final String path;
	private Class imageboard;
	private Class board;
	private Class thread;
	private Class post;
	private Class file;
	private ArrayList<Class> classes;


	/**
	 * Constructs Module object and find+load ModuleInfo.class.
	 *
	 * @param jar path to jar-file.
	 * @throws IOException            if I/O operation failed.
	 * @throws ClassNotFoundException if class not found.
	 */
	Module(String jar) throws IOException, ClassNotFoundException {
		java.io.File file = new java.io.File(jar);
		ClassLoader loader = new ClassLoader();
		this.path = file.getAbsolutePath();
		this.classes = loader.loadClassesFromJar(path, ModuleInfo.class.getSimpleName());
	}

	public Class getImageboard() {
		return imageboard;
	}

	public Class getBoard() {
		return board;
	}

	public Class getThread() {
		return thread;
	}

	public Class getPost() {
		return post;
	}

	public Class getFile() {
		return file;
	}

	public ArrayList<Class> getClasses() {
		return classes;
	}

	void loadAllModules(Class moduleInfoClass) throws NoSuchFieldException,
			IllegalAccessException, IOException, ClassNotFoundException {

		ClassLoader loader = new ClassLoader();
		Field imageboardNameField = moduleInfoClass.getField(ModuleInfo.imageboardName);
		Field boardNameField = moduleInfoClass.getField(ModuleInfo.boardName);
		Field threadNameField = moduleInfoClass.getField(ModuleInfo.threadName);
		Field postNameField = moduleInfoClass.getField(ModuleInfo.postName);
		Field fileNameField = moduleInfoClass.getField(ModuleInfo.fileName);
		this.imageboard = loader.loadClassesFromJar(path,
				(String) imageboardNameField.get(moduleInfoClass)).get(0);
		this.board = loader.loadClassesFromJar(path,
				(String) boardNameField.get(moduleInfoClass)).get(0);
		this.thread = loader.loadClassesFromJar(path,
				(String) threadNameField.get(moduleInfoClass)).get(0);
		this.post = loader.loadClassesFromJar(path,
				(String) postNameField.get(moduleInfoClass)).get(0);
		this.file = loader.loadClassesFromJar(path,
				(String) fileNameField.get(moduleInfoClass)).get(0);
	}
}
