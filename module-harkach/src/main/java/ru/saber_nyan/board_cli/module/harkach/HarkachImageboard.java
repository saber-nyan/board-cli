/******************************************************************************
 * Copyright © 2017 saber-nyan                                                *
 *                                                                            *
 * Licensed under the Apache License, Version 2.0 (the "License");            *
 * you may not use this file except in compliance with the License.           *
 * You may obtain a copy of the License at                                    *
 *                                                                            *
 *     http://www.apache.org/licenses/LICENSE-2.0                             *
 *                                                                            *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 ******************************************************************************/

package ru.saber_nyan.board_cli.module.harkach;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.saber_nyan.board_cli.module.ImageboardBoard;
import ru.saber_nyan.board_cli.module.ImageboardImageboard;
import ru.saber_nyan.board_cli.utils.HttpException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Boards list implementation for <i>2ch.hk</i>.
 *
 * @author saber-nyan
 */
@SuppressWarnings("WeakerAccess")
public class HarkachImageboard extends ImageboardImageboard {

	private static final String API_URL = "https://2ch.hk/makaba/mobile.fcgi?task=get_boards";
	private static final String KEY_IMAGEBOARD_BOARD_ABBREVIATION = "id";
	private static final String KEY_IMAGEBOARD_BOARD_TITLE = "name";

	/**
	 * Loads a list of boards.
	 *
	 * @param okHttpClient okHttp3 client
	 * @throws IOException   if GET request failed
	 *                       (try to cast to {@link HttpException} first!)
	 * @throws JSONException if received JSON is invalid
	 */
	public HarkachImageboard(@NotNull OkHttpClient okHttpClient) throws IOException, JSONException {
		super(okHttpClient);
		Request request = new Request.Builder()
				.url(API_URL)
				.get()
				.build();
		Response response = okHttpClient.newCall(request).execute();
		ResponseBody tempBody = response.body();
		String body = (tempBody != null ? tempBody.string() : "");
		if (!response.isSuccessful() || body.isEmpty()) {
			throw new HttpException(body, response.code());
		}

		JSONObject categories = new JSONObject(body);
		List<ImageboardBoard> boards = new ArrayList<>();

		categories.keys().forEachRemaining(categoryTitle -> {
			JSONArray boardsInCategory = categories.getJSONArray(categoryTitle);
			boardsInCategory.forEach(boardJsonObj -> {
				JSONObject boardJson;
				if (boardJsonObj instanceof JSONObject) {
					boardJson = (JSONObject) boardJsonObj;
				} else {
					throw new JSONException("Cast to JSONObject failed!");
				}
				ImageboardBoard imageboardBoard = new HarkachBoard(
						boardJson.getString(KEY_IMAGEBOARD_BOARD_ABBREVIATION),
						0,
						boardJson.optString(KEY_IMAGEBOARD_BOARD_TITLE, null),
						okHttpClient
				);
				boards.add(imageboardBoard);
			});
		});
		setBoards(boards);
	}
}
