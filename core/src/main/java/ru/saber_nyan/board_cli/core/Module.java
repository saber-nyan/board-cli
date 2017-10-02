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

import okhttp3.OkHttpClient;
import org.json.JSONObject;
import ru.saber_nyan.board_cli.module.ModuleInfo;
import ru.saber_nyan.board_cli.utils.ModuleLoader;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Second {@link ModuleLoader} part: {@link sun.reflect.Reflection}!
 */
@SuppressWarnings("WeakerAccess")
public class Module {

	private final String path;


	// Constructors (returned to Screens...)
	/**
	 * {@link ru.saber_nyan.board_cli.module.ImageboardImageboard} constructor.
	 */
	private Constructor<?> imageboard;
	/**
	 * {@link ru.saber_nyan.board_cli.module.ImageboardBoard} constructor.
	 */
	private Constructor<?> board;
	/**
	 * {@link ru.saber_nyan.board_cli.module.ImageboardThread} constructor.
	 */
	private Constructor<?> thread;
	/**
	 * {@link ru.saber_nyan.board_cli.module.ImageboardPost} constructor.
	 */
	private Constructor<?> post;
	/**
	 * {@link ru.saber_nyan.board_cli.module.ImageboardFile} constructor.
	 */
	private Constructor<?> file;

	// Classes (local use)
	private Class<?> imageboardCls;
	private Class<?> boardCls;
	private Class<?> threadCls;
	private Class<?> postCls;
	private Class<?> fileCls;

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

	/**
	 * @return {@link #imageboard}
	 */
	public Constructor<?> getImageboard() {
		return imageboard;
	}

	/**
	 * @return {@link #board}
	 */
	public Constructor<?> getBoard() {
		return board;
	}

	/**
	 * @return {@link #thread}
	 */
	public Constructor<?> getThread() {
		return thread;
	}

	/**
	 * @return {@link #post}
	 */
	public Constructor<?> getPost() {
		return post;
	}

	/**
	 * @return {@link #file}
	 */
	public Constructor<?> getFile() {
		return file;
	}

	ArrayList<Class> getClasses() {
		return classes;
	}

	/**
	 * <b>NANI?!</b>
	 * <p>
	 * Do not try to understand what is happening here. Really.
	 *
	 * @param moduleInfoClass chosen module class
	 * @throws NoSuchFieldException   fuсk
	 * @throws IllegalAccessException shit
	 * @throws IOException            work pls
	 * @throws ClassNotFoundException never happens
	 */
	void loadAllModules(Class moduleInfoClass) throws NoSuchFieldException,
			IllegalAccessException, IOException, ClassNotFoundException, NoSuchMethodException {

		Field imageboardNameField = moduleInfoClass.getField(ModuleInfo.imageboardName);
		Field boardNameField = moduleInfoClass.getField(ModuleInfo.boardName);
		Field threadNameField = moduleInfoClass.getField(ModuleInfo.threadName);
		Field postNameField = moduleInfoClass.getField(ModuleInfo.postName);
		Field fileNameField = moduleInfoClass.getField(ModuleInfo.fileName);
		this.imageboardCls = ModuleLoader.loadClassesFromJar(path,
				(String) imageboardNameField.get(moduleInfoClass)).get(0);
		this.boardCls = ModuleLoader.loadClassesFromJar(path,
				(String) boardNameField.get(moduleInfoClass)).get(0);
		this.threadCls = ModuleLoader.loadClassesFromJar(path,
				(String) threadNameField.get(moduleInfoClass)).get(0);
		this.postCls = ModuleLoader.loadClassesFromJar(path,
				(String) postNameField.get(moduleInfoClass)).get(0);
		this.fileCls = ModuleLoader.loadClassesFromJar(path,
				(String) fileNameField.get(moduleInfoClass)).get(0);
		loadConstructors();
	}

	/**
	 * Set up constructors.
	 */
	private void loadConstructors() throws NoSuchMethodException {
		imageboard = imageboardCls.getDeclaredConstructor(OkHttpClient.class);
		imageboard.setAccessible(true); // Speeds up Reflection

		board = boardCls.getDeclaredConstructor(
				String.class, long.class, String.class, OkHttpClient.class
		); //   abbreviation  page        title         okHttpClient
		board.setAccessible(true);

		thread = threadCls.getDeclaredConstructor(
				String.class, String.class, long.class, OkHttpClient.class
		); //   board        |thread       |thread     |okHttpClient
		//      abbreviation |title        |number
		thread.setAccessible(true);

		post = postCls.getDeclaredConstructor(
				JSONObject.class, OkHttpClient.class
		); //   post contents     okHttpClient
		post.setAccessible(true);

		file = fileCls.getDeclaredConstructor(
				JSONObject.class, OkHttpClient.class
		); //   file info         okHttpClient
		file.setAccessible(true);
	}
}
