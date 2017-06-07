package ru.saber_nyan.core;

import ru.saber_nyan.module.harkach.HarkachBoard;
import ru.saber_nyan.parser.Board;
import ru.saber_nyan.parser.Thread;

import java.io.PrintStream;

public class Main {

	private final static PrintStream err = System.err;
	private final static PrintStream out = System.out;

	public static void main(String[] args) {

		Board board = new HarkachBoard("", "s", 0);
		Thread thread;
		try {
			board.load();
			thread = board.getThreads().get(1);
			thread.load();
			out.println(board.getLongName());
			out.println(thread.getTitle());
		} catch (Exception e) {
			err.println("Can not load /s/index.json:");
			e.printStackTrace(err);
		}

	}
}
