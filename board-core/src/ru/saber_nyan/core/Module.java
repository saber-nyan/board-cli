package ru.saber_nyan.core;

import ru.saber_nyan.parser.ModuleInfo;
import ru.saber_nyan.utils.ClassLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Module {

	private Class board;
	private Class thread;
	private Class post;
	private Class file;
	private ArrayList<Class> classes;
	private String path;


	/**
	 * Constructs Module object and find&load ModuleInfo.class.
	 *
	 * @param jar path to jar-file.
	 * @throws IOException            if I/O operation failed.
	 * @throws ClassNotFoundException if class not found.
	 */
	public Module(String jar) throws IOException, ClassNotFoundException {
		java.io.File file = new java.io.File(jar);
		ClassLoader loader = new ClassLoader();
		this.path = file.getAbsolutePath();
		this.classes = loader.loadClassesFromJar(path, ModuleInfo.class.getSimpleName());
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

	public void loadAllModules(Class moduleInfoClass) throws NoSuchFieldException,
			IllegalAccessException, IOException, ClassNotFoundException {

		ClassLoader loader = new ClassLoader();
		Field boardNameField = moduleInfoClass.getField(ModuleInfo.boardName);
		Field threadNameField = moduleInfoClass.getField(ModuleInfo.threadName);
		Field postNameField = moduleInfoClass.getField(ModuleInfo.postName);
		Field fileNameField = moduleInfoClass.getField(ModuleInfo.fileName);
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
