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

package ru.saber_nyan.module.abstraction;

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
