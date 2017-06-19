/*******************************************************************************
 * Copyright (c) 2017 saber-nyan
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 ******************************************************************************/

package ru.saber_nyan.module.harkach;

import org.json.JSONException;
import org.json.JSONObject;
import ru.saber_nyan.parser.File;
import ru.saber_nyan.utils.Http;

public class HarkachFile extends File {

	private static final String API_URL = "https://2ch.hk";
	private static final String TMP_DIR = "/tmp/";
	private static final String KEY_FILE_NAME = "fullname";
	private static final String KEY_FILE_HEIGHT = "height";
	private static final String KEY_FILE_WIDTH = "width";
	private static final String KEY_FILE_DOWNLOAD_NAME = "name";
	private static final String KEY_FILE_DOWNLOAD_URL = "path";

	public HarkachFile(JSONObject file) {
		try {
			this.setName(file.optString(KEY_FILE_NAME));
		} catch (JSONException e) {
			this.setName(file.optString(KEY_FILE_DOWNLOAD_NAME));
		}
		this.setHeight(file.optLong(KEY_FILE_HEIGHT));
		this.setWidth(file.optLong(KEY_FILE_WIDTH));
		this.setDownloadName(file.optString(KEY_FILE_DOWNLOAD_NAME));
		this.setDownloadUrl(API_URL + file.optString(KEY_FILE_DOWNLOAD_URL));
	}

	@Override
	public void load() throws RuntimeException {
		Http http = new Http();
		http.sendGetBinaryToFile(this.getDownloadUrl(), TMP_DIR + this.getDownloadName());
		if (http.isError()) {
			throw new RuntimeException("(File loading) Error connecting to server!",
					http.getException());
		}
	}
}
