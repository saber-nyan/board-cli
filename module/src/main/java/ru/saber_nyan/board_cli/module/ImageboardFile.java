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

package ru.saber_nyan.board_cli.module;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Stores a file (of any type) attached to the post.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ImageboardFile {
	/**
	 * File name (with extension), usually a number.
	 */
	@NotNull
	private String filename = "";
	/**
	 * Only if the file is a picture.
	 */
	@Nullable
	private Long height;
	/**
	 * Only if the file is a picture.
	 */
	@Nullable
	private Long width;
	/**
	 * The address of the file to download.
	 */
	@NotNull
	private String url = "";
	/**
	 * Is {@link #load(String)} called?
	 */
	private boolean isLoaded = false;

	/**
	 * HTTP client.
	 */
	private final OkHttpClient okHttpClient;

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
	public ImageboardFile(@NotNull JSONObject file,
						  @NotNull OkHttpClient okHttpClient) throws JSONException {
		super();
		this.okHttpClient = okHttpClient;
	}

	/**
	 * @return {@link #filename}
	 */
	@NotNull
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename {@link #filename}
	 */
	public void setFilename(@NotNull String filename) {
		this.filename = filename;
	}

	/**
	 * @return {@link #height}
	 */
	@Nullable
	public Long getHeight() {
		return height;
	}

	/**
	 * @param height {@link #height}
	 */
	public void setHeight(@Nullable Long height) {
		this.height = height;
	}

	/**
	 * @return {@link #width}
	 */
	@Nullable
	public Long getWidth() {
		return width;
	}

	/**
	 * @param width {@link #width}
	 */
	public void setWidth(@Nullable Long width) {
		this.width = width;
	}

	/**
	 * @return {@link #url}
	 */
	@NotNull
	public String getUrl() {
		return url;
	}

	/**
	 * @param url {@link #url}
	 */
	public void setUrl(@NotNull String url) {
		this.url = url;
	}

	/**
	 * @return {@link #isLoaded}
	 */
	public boolean isLoaded() {
		return isLoaded;
	}

	/**
	 * @return {@link #okHttpClient}
	 */
	public OkHttpClient getOkHttpClient() {
		return okHttpClient;
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
	public String load(@NotNull String pathToSave) throws IllegalStateException, IOException {
		if (isLoaded) {
			throw new IllegalStateException("File is already loaded!");
		} else {
			this.isLoaded = true;
		}
		return "";
	}
}
