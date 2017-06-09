package ru.saber_nyan.parser;

import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Imageboard {
	private ArrayList<Board> boards;
	private String userAgent;

	public Imageboard(String userAgent) {
	}

	protected Imageboard() {
	}

	public ArrayList<Board> getBoards() {
		return boards;
	}

	public void setBoards(ArrayList<Board> boards) {
		this.boards = boards;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
