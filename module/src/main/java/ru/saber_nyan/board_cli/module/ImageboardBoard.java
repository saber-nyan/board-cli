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
 * Stores a list of threads from the board.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ImageboardBoard {
	/**
	 * Abbreviation of the board.
	 * <p>
	 * For example, <i>b</i> or <i>g</i>.
	 */
	@NotNull
	private String abbreviation = "";
	/**
	 * Page of the board.
	 * <p>
	 * The first page request is
	 * usually taken as <i>index.json</i>.
	 */
	private long page = 0;
	/**
	 * Title of the board.
	 */
	@Nullable
	private String title = null;
	/**
	 * Obviously!
	 */
	private long bumplimit = 0;
	/**
	 * Threads in the board or null if they are
	 * not already loaded using {@link #load()}.
	 */
	@Nullable
	private List<ImageboardThread> threads = null;
	/**
	 * Is {@link #load()} called?
	 */
	private boolean isLoaded = false;
	/**
	 * HTTP client.
	 */
	@NotNull
	private final OkHttpClient okHttpClient;

	/**
	 * To load the treads, use {@link #load()}!
	 *
	 * @param abbreviation {@link #abbreviation board ID}
	 * @param page         {@link #page board page}
	 * @param title        {@link #title board title}
	 * @param okHttpClient okHttp3 client
	 */
	public ImageboardBoard(@NotNull String abbreviation, long page, @Nullable String title,
						   @NotNull OkHttpClient okHttpClient) {
		super();
		this.abbreviation = abbreviation;
		this.page = page;
		this.title = title;
		this.okHttpClient = okHttpClient;
	}

	/**
	 * @return {@link #abbreviation}
	 */
	@NotNull
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * @param abbreviation {@link #abbreviation}
	 */
	public void setAbbreviation(@NotNull String abbreviation) {
		this.abbreviation = abbreviation;
	}

	/**
	 * @return {@link #page}
	 */
	public long getPage() {
		return page;
	}

	/**
	 * @param page {@link #page}
	 */
	public void setPage(long page) {
		this.page = page;
	}

	/**
	 * @return {@link #title}
	 */
	@Nullable
	public String getTitle() {
		return title;
	}

	/**
	 * @param title {@link #title}
	 */
	public void setTitle(@Nullable String title) {
		this.title = title;
	}

	/**
	 * @return {@link #bumplimit}
	 */
	public long getBumplimit() {
		return bumplimit;
	}

	/**
	 * @param bumplimit {@link #bumplimit}
	 */
	public void setBumplimit(long bumplimit) {
		this.bumplimit = bumplimit;
	}

	/**
	 * @return {@link #threads}
	 */
	@Nullable
	public List<ImageboardThread> getThreads() {
		return threads;
	}

	/**
	 * @param threads {@link #threads}
	 */
	public void setThreads(@Nullable List<ImageboardThread> threads) {
		this.threads = threads;
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
	@NotNull
	public OkHttpClient getOkHttpClient() {
		return okHttpClient;
	}

	/**
	 * Loads entire board, creates {@link ImageboardThread} objects.
	 *
	 * @throws IllegalStateException if thread is already loaded
	 * @throws IOException           if GET request failed
	 *                               (try to cast to {@code utils.HttpException} first!)
	 * @throws JSONException         if received JSON is invalid
	 */
	public void load() throws IllegalStateException, IOException, JSONException {
		if (isLoaded) {
			throw new IllegalStateException("Board is already loaded!");
		} else {
			this.isLoaded = true;
		}
	}
}
