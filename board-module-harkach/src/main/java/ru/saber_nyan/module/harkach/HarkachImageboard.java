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
import ru.saber_nyan.module.abstraction.Imageboard;
import ru.saber_nyan.utils.Http;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings({"unused", "WeakerAccess"})
public class HarkachImageboard extends Imageboard {
	private static final String API_URL =
			"https://2ch.hk/makaba/mobile.fcgi?task=get_boards";
	private static final String KEY_IMAGEBOARD_BOARD_NAME = "id";
	private static final String KEY_IMAGEBOARD_BOARD_LONG_NAME = "name";

	public HarkachImageboard(String userAgent) {
		this.setUserAgent(userAgent);
		Http http = new Http();
		http.sendGet(API_URL, userAgent);

		if (http.isError()) {
			throw new RuntimeException("(Imageboard loading) Error connecting to server!",
					http.getException());
		}

		JSONObject jsonObject = new JSONObject(http.getResult());
		Iterator<String> keys = jsonObject.keys();

		ArrayList<Board> boards = new ArrayList<>();

		keys.forEachRemaining(s -> {
			JSONArray jsonArray = jsonObject.getJSONArray(s);
			for (Object o : jsonArray) {
				Board board = new HarkachBoard(userAgent,
						((JSONObject) o).getString(KEY_IMAGEBOARD_BOARD_NAME),
						((JSONObject) o).getString(KEY_IMAGEBOARD_BOARD_LONG_NAME),
						0);
				boards.add(board);
			}
		});
		this.setBoards(boards);
	}
}
