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
 * Stores one thread of board.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ImageboardThread {
	/**
	 * Parent board abbreviation for thread loading.
	 */
	@NotNull
	private final String boardAbbreviation;
	/**
	 * Sometimes the first post is given in the list of threads.
	 */
	@Nullable
	private ImageboardPost opPost = null;
	/**
	 * <i>Supported by some imageboards.</i>
	 * <p>
	 * Thread title.
	 */
	@Nullable
	private String title = null;
	/**
	 * Unique (on the board) thread number.
	 */
	private long number = 0;
	/**
	 * Number of posts in the thread.
	 * <p>
	 * Also we can use {@link List#size() posts.size()}.
	 */
	private long postCount = 0;
	/**
	 * Number of posts with files.
	 */
	private long fileCount = 0;
	/**
	 * Posts in the thread or null if they are
	 * not already loaded using {@link #load()}.
	 * <p>
	 * {@link List#get(int) posts.get(0)} is an OP-post.
	 */
	@Nullable
	private List<ImageboardPost> posts = null;
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
	 * To load the thread, use {@link #load()}!
	 *
	 * @param boardAbbreviation {@link #boardAbbreviation}
	 * @param title             {@link #title}
	 * @param opPost            {@link #opPost}
	 * @param number            {@link #number}
	 * @param okHttpClient      {@link #okHttpClient}
	 */
	public ImageboardThread(@NotNull String boardAbbreviation, @Nullable String title,
							@Nullable ImageboardPost opPost, long number,
							@NotNull OkHttpClient okHttpClient) {
		super();
		this.boardAbbreviation = boardAbbreviation;
		this.title = title;
		this.opPost = opPost;
		this.number = number;
		this.okHttpClient = okHttpClient;
	}

	/**
	 * @return {@link #boardAbbreviation}
	 */
	@NotNull
	public String getBoardAbbreviation() {
		return boardAbbreviation;
	}

	/**
	 * @return {@link #opPost}
	 */
	@Nullable
	public ImageboardPost getOpPost() {
		return opPost;
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
	 * @return {@link #number}
	 */
	public long getNumber() {
		return number;
	}

	/**
	 * @param number {@link #number}
	 */
	public void setNumber(long number) {
		this.number = number;
	}

	/**
	 * @return {@link #postCount}
	 */
	public long getPostCount() {
		return postCount;
	}

	/**
	 * @param postCount {@link #postCount}
	 */
	public void setPostCount(long postCount) {
		this.postCount = postCount;
	}

	/**
	 * @return {@link #fileCount}
	 */
	public long getFileCount() {
		return fileCount;
	}

	/**
	 * @param fileCount {@link #fileCount}
	 */
	public void setFileCount(long fileCount) {
		this.fileCount = fileCount;
	}

	/**
	 * @return {@link #posts}
	 */
	@Nullable
	public List<ImageboardPost> getPosts() {
		return posts;
	}

	/**
	 * @param posts {@link #posts}
	 */
	public void setPosts(@Nullable List<ImageboardPost> posts) {
		this.posts = posts;
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
	 * Loads entire thread, creates {@link ImageboardPost} objects.
	 *
	 * @throws IllegalStateException if thread is already loaded
	 * @throws IOException           if GET request failed
	 *                               (try to cast to {@code utils.HttpException} first!)
	 * @throws JSONException         if received JSON is invalid
	 */
	public void load() throws IllegalStateException, IOException, JSONException {
		if (isLoaded) {
			throw new IllegalStateException("Thread is already loaded!");
		} else {
			this.isLoaded = true;
		}
	}
}
