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

import java.io.IOException;
import java.util.List;

/**
 * Contains a list of boards of imageboard.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ImageboardImageboard {
	/**
	 * List of boards.
	 */
	@Nullable
	private List<ImageboardBoard> boards = null;

	/**
	 * Loads a list of boards.
	 *
	 * @param okHttpClient okHttp3 client
	 * @throws IOException   if GET request failed
	 *                       (try to cast to {@code utils.HttpException} first!)
	 * @throws JSONException if received JSON is invalid
	 */
	public ImageboardImageboard(@NotNull OkHttpClient okHttpClient)
			throws IOException, JSONException {
		super();
	}

	/**
	 * @return {@link #boards}
	 */
	@Nullable
	public List<ImageboardBoard> getBoards() {
		return boards;
	}

	/**
	 * @param boards {@link #boards}
	 */
	public void setBoards(@Nullable List<ImageboardBoard> boards) {
		this.boards = boards;
	}
}
