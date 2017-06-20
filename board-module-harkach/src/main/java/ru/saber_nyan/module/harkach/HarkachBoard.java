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
import ru.saber_nyan.module.abstraction.Board;
import ru.saber_nyan.module.abstraction.Thread;
import ru.saber_nyan.utils.Http;

import java.util.ArrayList;

public class HarkachBoard extends Board {

	private static final String API_URL = "https://2ch.hk/%s/%s.json";
	private static final String KEY_BOARD_BUMP_LIMIT = "bump_limit";
	private static final String KEY_BOARD_THREADS = "threads";
	private static final String KEY_BOARD_THREAD_NUM = "thread_num";

	public HarkachBoard(String userAgent, String boardName, String boardDesc, long boardPage) {
		this.setUserAgent(userAgent);
		this.setName(boardName);
		this.setLongName(boardDesc);
		this.setPage(boardPage);
	}

	@Override
	public void load() throws RuntimeException {
		Http http = new Http();
		String pageStr;
		long page = this.getPage();
		if (page == 0) {
			pageStr = "index";
		} else {
			pageStr = String.valueOf(page);
		}
		String url = String.format(API_URL, this.getName(), pageStr);
		http.sendGet(url, this.getUserAgent());

		if (http.isError()) {
			throw new RuntimeException("(Board loading) Error connecting to server!",
					http.getException());
		}

		JSONObject jsonObject = new JSONObject(http.getResult());
		this.setBumpLimit(jsonObject.optLong(KEY_BOARD_BUMP_LIMIT));

		JSONArray threadsArray = jsonObject.optJSONArray(KEY_BOARD_THREADS);
		long threadsCount = threadsArray.length();

		ArrayList<Thread> threads = new ArrayList<>();

		for (long i = 0; i < threadsCount; i++) {
			Thread newThread = new HarkachThread(this.getName(),
					threadsArray.getJSONObject((int) i).getLong(KEY_BOARD_THREAD_NUM),
					this.getUserAgent());
			threads.add(newThread);
		}

		this.setThreads(threads);
	}
}
