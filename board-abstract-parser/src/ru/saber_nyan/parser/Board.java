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
