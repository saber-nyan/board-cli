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

package ru.saber_nyan.module.harkach;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.saber_nyan.parser.File;
import ru.saber_nyan.parser.Post;

import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
public class HarkachPost extends Post {

	private static final String KEY_POST_NUMBER = "num";
	private static final String KEY_POST_BANNED = "banned";
	private static final String KEY_POST_CLOSED = "closed";
	private static final String KEY_POST_COMMENT = "comment";
	private static final String KEY_POST_DATE = "date";
	private static final String KEY_POST_EMAIL = "email";
	private static final String KEY_POST_NAME = "name";
	private static final String KEY_POST_SUBJECT = "subject";
	private static final String KEY_POST_TRIPCODE = "trip";
	private static final String KEY_POST_OP = "op";
	private static final String KEY_POST_STICKY = "sticky";
	private static final String KEY_POST_FILES = "files";

	public HarkachPost(JSONObject post) {
		this.setNumber(post.optLong(KEY_POST_NUMBER));
		this.setBanned(post.optInt(KEY_POST_BANNED) != 0);
		this.setClosed(post.optInt(KEY_POST_CLOSED) != 0);
		this.setOp(post.optInt(KEY_POST_OP) != 0);
		this.setSticky(post.optInt(KEY_POST_STICKY) != 0);
		this.setComment(post.optString(KEY_POST_COMMENT));
		this.setDate(post.optString(KEY_POST_DATE));
		this.setEmail(post.optString(KEY_POST_EMAIL));
		this.setName(post.optString(KEY_POST_NAME));
		this.setSubject(post.optString(KEY_POST_SUBJECT));
		this.setTripcode(post.optString(KEY_POST_TRIPCODE));

		JSONArray filesArray = post.optJSONArray(KEY_POST_FILES);

		ArrayList<File> files = new ArrayList<>();

		for (int i = 0; i < filesArray.length(); i++) {
			File newFile = new HarkachFile(filesArray.getJSONObject(i));
			files.add(newFile);
		}

		this.setFiles(files);
	}
}
