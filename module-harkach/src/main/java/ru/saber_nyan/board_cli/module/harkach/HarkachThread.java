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
import ru.saber_nyan.board_cli.module.ImageboardPost;
import ru.saber_nyan.board_cli.module.ImageboardThread;
import ru.saber_nyan.board_cli.utils.HttpException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Thread implementation for <i>2ch.hk</i>.
 *
 * @author saber-nyan
 */
@SuppressWarnings("WeakerAccess")
public class HarkachThread extends ImageboardThread {

	private static final String API_URL = "https://2ch.hk/%s/res/%s.json";
	private static final String KEY_THREADS = "threads";
	private static final String KEY_THREAD_TITLE = "title";
	private static final String KEY_THREAD_POST_COUNT = "posts_count";
	private static final String KEY_THREAD_FILE_COUNT = "files_count";
	private static final String KEY_THREAD_POSTS = "posts";

	/**
	 * To load the thread, use {@link #load()}!
	 *
	 * @param boardAbbreviation {@link #boardAbbreviation}
	 * @param title             {@link #title}
	 * @param opPost            {@link #opPost}
	 * @param number            {@link #number}
	 * @param okHttpClient      {@link #okHttpClient}
	 */
	public HarkachThread(@NotNull String boardAbbreviation, @Nullable String title,
						 @Nullable ImageboardPost opPost, long number,
						 @NotNull OkHttpClient okHttpClient) {
		super(boardAbbreviation, title, opPost, number, okHttpClient);
	}

	/**
	 * Loads entire thread, creates {@link ImageboardPost} objects.
	 *
	 * @throws IllegalStateException if thread is already loaded
	 * @throws IOException           if GET request failed
	 *                               (try to cast to {@code utils.HttpException} first!)
	 * @throws JSONException         if received JSON is invalid
	 */
	@Override
	public void load() throws IllegalStateException, IOException, JSONException {
		super.load();

		String url = String.format(API_URL, getBoardAbbreviation(), String.valueOf(getNumber()));
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

		JSONObject thread = new JSONObject(body);
		setTitle(thread.optString(KEY_THREAD_TITLE, null));
		setPostCount(thread.optLong(KEY_THREAD_POST_COUNT));
		setFileCount(thread.optLong(KEY_THREAD_FILE_COUNT));

		// Кривые API макаки как обычно.
		// Получить ОП-пост: {thread} -> [threads] -> {0} -> [posts] -> {0}
		// Пиздец, да?
		JSONObject thisThread = thread.getJSONArray(KEY_THREADS).getJSONObject(0);
		JSONArray postsJson = thisThread.getJSONArray(KEY_THREAD_POSTS);

		List<ImageboardPost> posts = new ArrayList<>(postsJson.length());

		postsJson.forEach(postJsonObj -> {
			JSONObject postJson;
			if (postJsonObj instanceof JSONObject) {
				postJson = (JSONObject) postJsonObj;
			} else {
				throw new JSONException("Cast to JSONObject failed!");
			}
			ImageboardPost post = new HarkachPost(postJson, getOkHttpClient());
			posts.add(post);
		});
		setPosts(posts);
	}
}
