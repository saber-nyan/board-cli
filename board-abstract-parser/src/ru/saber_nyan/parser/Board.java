package ru.saber_nyan.parser;

import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Board {
	private String name;
	private long page;
	private String longName;
	private long bumpLimit;
	private ArrayList<Thread> threads;
	private String userAgent;

	public Board(String userAgent, String boardName, String boardDesc, long boardPage) {
	}

	protected Board() {
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public long getBumpLimit() {
		return bumpLimit;
	}

	public void setBumpLimit(long bumpLimit) {
		this.bumpLimit = bumpLimit;
	}

	public ArrayList<Thread> getThreads() {
		return threads;
	}

	public void setThreads(ArrayList<Thread> threads) {
		this.threads = threads;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public abstract void load() throws RuntimeException;
}
