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
import org.json.JSONException;
import org.json.JSONObject;
import ru.saber_nyan.board_cli.module.ImageboardFile;
import ru.saber_nyan.board_cli.utils.HttpException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Attached file implementation for <i>2ch.hk</i>.
 *
 * @author saber-nyan
 */
@SuppressWarnings("WeakerAccess")
public class HarkachFile extends ImageboardFile {

	private static final String API_URL = "https://2ch.hk";
	private static final String KEY_FILE_NAME = "fullname";
	private static final String KEY_FILE_NAME_ALT = "name";
	private static final String KEY_FILE_HEIGHT = "height";
	private static final String KEY_FILE_WIDTH = "width";
	private static final String KEY_FILE_DOWNLOAD_URL = "path";

	/**
	 * Information about the attachment
	 * is taken from the {@code file}.
	 * <p>
	 * Call {@link #load(String)} to save file to the disk.
	 *
	 * @param file         file info
	 * @param okHttpClient okHttp3 client
	 * @throws JSONException if received JSON is invalid
	 */
	public HarkachFile(@NotNull JSONObject file,
					   @NotNull OkHttpClient okHttpClient) throws JSONException {
		super(file, okHttpClient);

		String filename = file.optString(KEY_FILE_NAME, null);
		if (filename == null) {
			filename = file.getString(KEY_FILE_NAME_ALT);
		}
		setFilename(filename);

		Object h = file.opt(KEY_FILE_HEIGHT);
		if (h != null) {
			setHeight((long) (int) h);
		}

		Object w = file.opt(KEY_FILE_WIDTH);
		if (w != null) {
			setWidth((long) (int) w);
		}
		setUrl(API_URL + file.getString(KEY_FILE_DOWNLOAD_URL));
	}

	/**
	 * Saves file to disk.
	 *
	 * @param pathToSave the path to the directory
	 *                   where the file will be saved.
	 * @return path to downloaded file
	 * @throws IllegalStateException if file is already loaded
	 * @throws IOException           if GET request failed
	 */
	@NotNull
	@Override
	public String load(@NotNull String pathToSave) throws IllegalStateException, IOException {
		super.load(pathToSave);
		Request request = new Request.Builder()
				.url(getUrl())
				.get()
				.build();
		Response response = getOkHttpClient().newCall(request).execute();
		ResponseBody body = response.body();
		if (!response.isSuccessful() || body == null) {
			throw new HttpException(response.code());
		}

		String outFilePath = pathToSave + File.separator + getFilename();
		try (FileOutputStream outputStream = new FileOutputStream(outFilePath)) {
			outputStream.write(body.bytes());
		}
		return outFilePath;
	}
}
