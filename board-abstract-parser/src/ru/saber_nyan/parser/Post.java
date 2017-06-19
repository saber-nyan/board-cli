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

import org.json.JSONObject;

import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Post {
	private long number;
	private boolean banned;
	private boolean closed;
	private String comment;
	private String date;
	private String email;
	private ArrayList<File> files;
	private String name;
	private boolean op;
	private boolean sticky;
	private String subject;
	private String tripcode;

	public Post(JSONObject post) {
	}

	protected Post() {
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOp() {
		return op;
	}

	public void setOp(boolean op) {
		this.op = op;
	}

	public boolean isSticky() {
		return sticky;
	}

	public void setSticky(boolean sticky) {
		this.sticky = sticky;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTripcode() {
		return tripcode;
	}

	public void setTripcode(String tripcode) {
		this.tripcode = tripcode;
	}
}
