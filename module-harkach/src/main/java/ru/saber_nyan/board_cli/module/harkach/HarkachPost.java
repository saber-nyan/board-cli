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
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.saber_nyan.board_cli.module.ImageboardFile;
import ru.saber_nyan.board_cli.module.ImageboardPost;

import java.util.ArrayList;

/**
 * Post implementation for <i>2ch.hk</i>.
 *
 * @author saber-nyan
 */
@SuppressWarnings("WeakerAccess")
public class HarkachPost extends ImageboardPost {


	private static final String KEY_NUMBER = "num";
	private static final String KEY_BANNED = "banned";
	private static final String KEY_CLOSED = "closed";
	private static final String KEY_COMMENT = "comment";
	private static final String KEY_DATE = "date";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_NAME = "name";
	private static final String KEY_SUBJECT = "subject";
	private static final String KEY_TRIPCODE = "trip";
	private static final String KEY_OP = "op";
	private static final String KEY_STICKY = "sticky";
	private static final String KEY_FILES = "files";

	/**
	 * Class doesn't work with network, the content is
	 * taken from {@code post}.
	 *
	 * @param post         post content
	 * @param okHttpClient okHttp3 client for attachment
	 *                     downloading
	 * @throws JSONException if received JSON is invalid
	 */
	public HarkachPost(@NotNull JSONObject post,
					   @NotNull OkHttpClient okHttpClient) throws JSONException {
		super(post, okHttpClient);

		setNumber(post.getLong(KEY_NUMBER));
		setBanned(post.optInt(KEY_BANNED) != 0);
		setClosed(post.optInt(KEY_CLOSED) != 0); // Даже представить не могу, почему макака
		setOp(post.optInt(KEY_OP) != 0);         // хранит это не в boolean...
		setSticky(post.optInt(KEY_STICKY) != 0);
		setComment(post.getString(KEY_COMMENT));
		setDate(post.getString(KEY_DATE));
		setEmail(post.optString(KEY_EMAIL, null));
		setName(post.optString(KEY_NAME, null));
		setSubject(post.optString(KEY_SUBJECT, null));
		setTripcode(post.optString(KEY_TRIPCODE, null));

		JSONArray filesArray = post.optJSONArray(KEY_FILES);
		if (filesArray != null) {
			ArrayList<ImageboardFile> files = new ArrayList<>(filesArray.length());
			filesArray.forEach(fileJsonObj -> {
				JSONObject fileJson;
				if (fileJsonObj instanceof JSONObject) {
					fileJson = (JSONObject) fileJsonObj;
				} else {
					throw new JSONException("Cast to JSONObject failed!");
				}
				ImageboardFile file = new HarkachFile(fileJson, okHttpClient);
				files.add(file);
			});
			setFiles(files);
		}
	}
}
