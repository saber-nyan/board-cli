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
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.saber_nyan.board_cli.module.ImageboardBoard;
import ru.saber_nyan.board_cli.module.ImageboardPost;
import ru.saber_nyan.board_cli.module.ImageboardThread;
import ru.saber_nyan.board_cli.utils.HttpException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Threads list implementation for <i>2ch.hk</i>.
 *
 * @author saber-nyan
 */
@SuppressWarnings("WeakerAccess")
public class HarkachBoard extends ImageboardBoard {

	private static final String API_URL = "https://2ch.hk/%s/%s.json";
	private static final String KEY_BOARD_BUMP_LIMIT = "bump_limit";
	private static final String KEY_BOARD_THREADS = "threads";
	private static final String KEY_BOARD_THREAD_NUM = "thread_num";
	private static final String KEY_THREAD_POSTS = "posts";
	private static final String KEY_THREAD_SUBJECT = "subject";

	/**
	 * To load the tread, use {@link #load()}!
	 *
	 * @param abbreviation {@link #abbreviation board ID}
	 * @param page         {@link #page board page}
	 * @param title        {@link #title board title}
	 * @param okHttpClient okHttp3 client
	 */
	public HarkachBoard(@NotNull String abbreviation, long page, @Nullable String title,
						@NotNull OkHttpClient okHttpClient) {
		super(abbreviation, page, title, okHttpClient);
	}

	/**
	 * Loads entire board, creates {@link HarkachThread} objects.
	 *
	 * @throws IOException           if GET request failed
	 *                               (try to cast to {@code utils.HttpException} first!)
	 * @throws JSONException         if received JSON is invalid
	 */
	@Override
	public void load() throws IOException, JSONException {
		super.load();

		String pageStr;
		if (getPage() == 0) {
			pageStr = "index";
		} else {
			pageStr = String.valueOf(getPage());
		}

		String url = String.format(API_URL, getAbbreviation(), pageStr);
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		Response response = getOkHttpClient().newCall(request).execute();
		ResponseBody tempBody = response.body();
		String body = (tempBody != null ? tempBody.string() : "");
		if (!response.isSuccessful() || body.isEmpty()) {
			throw new HttpException(body, response.code());
		}

		JSONObject board = new JSONObject(body);
		setBumplimit(board.optLong(KEY_BOARD_BUMP_LIMIT));

		JSONArray threadsJson = board.getJSONArray(KEY_BOARD_THREADS);

		List<ImageboardThread> threads = new ArrayList<>(threadsJson.length());

		threadsJson.forEach(threadJsonObj -> {
			JSONObject threadJson;
			if (threadJsonObj instanceof JSONObject) {
				threadJson = (JSONObject) threadJsonObj;
			} else {
				throw new JSONException("Cast to JSONObject failed!");
			}

			String threadTitle = threadJson.getJSONArray(KEY_THREAD_POSTS).getJSONObject(0)
					.optString(KEY_THREAD_SUBJECT, null);
			ImageboardPost opPost = new HarkachPost(threadJson.getJSONArray(KEY_THREAD_POSTS).getJSONObject(0),
					getOkHttpClient());
			ImageboardThread imageboardThread = new HarkachThread(
					getAbbreviation(),
					threadTitle,
					opPost,
					threadJson.getLong(KEY_BOARD_THREAD_NUM),
					getOkHttpClient()
			);
			threads.add(imageboardThread);
		});
		setThreads(threads);
	}
}
