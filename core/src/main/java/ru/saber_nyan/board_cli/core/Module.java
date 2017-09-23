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

import ru.saber_nyan.board_cli.module.ModuleInfo;
import ru.saber_nyan.board_cli.utils.ModuleLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

class Module {

	private final String path;
	private Class imageboard;
	private Class board;
	private Class thread;
	private Class post;
	private Class file;
	private final ArrayList<Class> classes;


	/**
	 * Constructs Module object and find+load ModuleInfo.class.
	 *
	 * @param jar path to jar-file.
	 * @throws IOException            if I/O operation failed
	 * @throws ClassNotFoundException if class not found
	 */
	Module(String jar) throws IOException, ClassNotFoundException {
		super();
		java.io.File file = new java.io.File(jar);
		this.path = file.getAbsolutePath();
		this.classes = ModuleLoader.loadClassesFromJar(path, ModuleInfo.class.getSimpleName());
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

	/**
	 * <b>NANI?!</b>
	 * <p>
	 * Do not try to understand what is happening here. Really.
	 *
	 * @param moduleInfoClass chosen module class
	 * @throws NoSuchFieldException   fuck
	 * @throws IllegalAccessException shit
	 * @throws IOException            work pls
	 * @throws ClassNotFoundException never happens
	 */
	void loadAllModules(Class moduleInfoClass) throws NoSuchFieldException,
			IllegalAccessException, IOException, ClassNotFoundException {

		Field imageboardNameField = moduleInfoClass.getField(ModuleInfo.imageboardName);
		Field boardNameField = moduleInfoClass.getField(ModuleInfo.boardName);
		Field threadNameField = moduleInfoClass.getField(ModuleInfo.threadName);
		Field postNameField = moduleInfoClass.getField(ModuleInfo.postName);
		Field fileNameField = moduleInfoClass.getField(ModuleInfo.fileName);
		this.imageboard = ModuleLoader.loadClassesFromJar(path,
				(String) imageboardNameField.get(moduleInfoClass)).get(0);
		this.board = ModuleLoader.loadClassesFromJar(path,
				(String) boardNameField.get(moduleInfoClass)).get(0);
		this.thread = ModuleLoader.loadClassesFromJar(path,
				(String) threadNameField.get(moduleInfoClass)).get(0);
		this.post = ModuleLoader.loadClassesFromJar(path,
				(String) postNameField.get(moduleInfoClass)).get(0);
		this.file = ModuleLoader.loadClassesFromJar(path,
				(String) fileNameField.get(moduleInfoClass)).get(0);
	}
}
