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
import ru.saber_nyan.module.abstraction.Post;
import ru.saber_nyan.module.abstraction.Thread;
import ru.saber_nyan.utils.Http;

import java.util.ArrayList;

public class HarkachThread extends Thread {

	private static final String API_URL = "https://2ch.hk/%s/res/%s.json";
	private static final String KEY_THREADS = "threads";
	private static final String KEY_THREAD_TITLE = "title";
	private static final String KEY_THREAD_POST_COUNT = "posts_count";
	private static final String KEY_THREAD_FILE_COUNT = "files_count";
	private static final String KEY_THREAD_POSTS = "posts";

	public HarkachThread(String boardName, long threadNum, String userAgent) {
		this.setBoardName(boardName);
		this.setNumber(threadNum);
		this.setUserAgent(userAgent);
	}

	@Override
	public void load() throws RuntimeException {
		Http http = new Http();
		String url = String.format(API_URL, this.getBoardName(), String.valueOf(this.getNumber()));
		http.sendGet(url, this.getUserAgent());

		if (http.isError()) {
			throw new RuntimeException("(Thread loading) Error connecting to server!",
					http.getException());
		}

		JSONObject jsonObject = new JSONObject(http.getResult());
		this.setTitle(jsonObject.optString(KEY_THREAD_TITLE));
		this.setPostCount(jsonObject.optLong(KEY_THREAD_POST_COUNT));
		this.setFileCount(jsonObject.optLong(KEY_THREAD_FILE_COUNT));

		JSONObject thisThread = jsonObject.optJSONArray(KEY_THREADS).getJSONObject(0);
		JSONArray postsArray = thisThread.optJSONArray(KEY_THREAD_POSTS);
		long postsCount = postsArray.length();

		ArrayList<Post> posts = new ArrayList<>();

		for (long i = 0; i < postsCount; i++) {
			Post newPost = new HarkachPost(postsArray.optJSONObject((int) i));
			posts.add(newPost);
		}

		this.setPosts(posts);
	}
}
