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

import java.util.List;

/**
 * Stores one post of thread, can be an OP-post.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ImageboardPost {
	/**
	 * Unique (on the board) post number.
	 */
	private long number = 0;
	/**
	 * Supported by some imageboards.
	 */
	private boolean isBanned = false;
	/**
	 * <i>If this is an OP-post.
	 * Supported by some imageboards.</i>
	 * <p>
	 * Can user post in the thread?
	 */
	private boolean isClosed = false;
	/**
	 * <i>If this is an OP-post.
	 * Supported by some imageboards.</i>
	 * <p>
	 * Is thread always on top?
	 */
	private boolean isSticky = false;
	/**
	 * Is this an OP-post?
	 */
	private boolean isOp = false;
	/**
	 * Files of any type attached to the post or {@code null}.
	 */
	@Nullable
	private List<ImageboardFile> files = null;
	/**
	 * Date of the post.
	 */
	@NotNull
	private String date = "";
	/**
	 * Subject of the post.
	 */
	@Nullable
	private String subject = null;
	/**
	 * <i>Supported by some imageboards.</i>
	 * <p>
	 * User tripcode.
	 */
	@Nullable
	private String tripcode = null;
	/**
	 * Email of the post or <i>sage</i>.
	 */
	@Nullable
	private String email = null;
	/**
	 * User name (w/o tripcode).
	 */
	@Nullable
	private String name = null;

	/**
	 * Contents of the post.
	 */
	@NotNull
	private String comment = "";

	/**
	 * Class doesn't work with network, the content is
	 * taken from {@code post}.
	 *
	 * @param post         post content
	 * @param okHttpClient okHttp3 client for attachment
	 *                     downloading
	 * @throws JSONException if received JSON is invalid
	 */
	public ImageboardPost(@NotNull JSONObject post,
						  @NotNull OkHttpClient okHttpClient) throws JSONException {
		super();
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
	 * @return {@link #isBanned}
	 */
	public boolean isBanned() {
		return isBanned;
	}

	/**
	 * @param banned {@link #isBanned}
	 */
	public void setBanned(boolean banned) {
		isBanned = banned;
	}

	/**
	 * @return {@link #isClosed}
	 */
	public boolean isClosed() {
		return isClosed;
	}

	/**
	 * @param closed {@link #isClosed}
	 */
	public void setClosed(boolean closed) {
		isClosed = closed;
	}

	/**
	 * @return {@link #isSticky}
	 */
	public boolean isSticky() {
		return isSticky;
	}

	/**
	 * @param sticky {@link #isSticky}
	 */
	public void setSticky(boolean sticky) {
		isSticky = sticky;
	}

	/**
	 * @return {@link #isOp}
	 */
	public boolean isOp() {
		return isOp;
	}

	/**
	 * @param op {@link #isOp}
	 */
	public void setOp(boolean op) {
		isOp = op;
	}

	/**
	 * @return {@link #files}
	 */
	@Nullable
	public List<ImageboardFile> getFiles() {
		return files;
	}

	/**
	 * @param files {@link #files}
	 */
	public void setFiles(@Nullable List<ImageboardFile> files) {
		this.files = files;
	}

	/**
	 * @return {@link #date}
	 */
	@NotNull
	public String getDate() {
		return date;
	}

	/**
	 * @param date {@link #date}
	 */
	public void setDate(@NotNull String date) {
		this.date = date;
	}

	/**
	 * @return {@link #subject}
	 */
	@Nullable
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject {@link #subject}
	 */
	public void setSubject(@Nullable String subject) {
		this.subject = subject;
	}

	/**
	 * @return {@link #tripcode}
	 */
	@Nullable
	public String getTripcode() {
		return tripcode;
	}

	/**
	 * @param tripcode {@link #tripcode}
	 */
	public void setTripcode(@Nullable String tripcode) {
		this.tripcode = tripcode;
	}

	/**
	 * @return {@link #email}
	 */
	@Nullable
	public String getEmail() {
		return email;
	}

	/**
	 * @param email {@link #email}
	 */
	public void setEmail(@Nullable String email) {
		this.email = email;
	}

	/**
	 * @return {@link #name}
	 */
	@Nullable
	public String getName() {
		return name;
	}

	/**
	 * @param name {@link #name}
	 */
	public void setName(@Nullable String name) {
		this.name = name;
	}

	/**
	 * @return {@link #comment}
	 */
	@NotNull
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment {@link #comment}
	 */
	public void setComment(@NotNull String comment) {
		this.comment = comment;
	}
}
