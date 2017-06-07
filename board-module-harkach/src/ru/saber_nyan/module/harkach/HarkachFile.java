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
			this.setName(file.getString(KEY_FILE_NAME));
		} catch (JSONException e) {
			this.setName(file.getString(KEY_FILE_DOWNLOAD_NAME));
		}
		this.setHeight(file.getLong(KEY_FILE_HEIGHT));
		this.setWidth(file.getLong(KEY_FILE_WIDTH));
		this.setDownloadName(file.getString(KEY_FILE_DOWNLOAD_NAME));
		this.setDownloadUrl(API_URL + file.getString(KEY_FILE_DOWNLOAD_URL));
	}

	@Override
	public void load() throws Exception {
		Http http = new Http();
		http.sendGetBinaryToFile(this.getDownloadUrl(), TMP_DIR + this.getDownloadName());
		if (http.isError()) {
			throw http.getException();
		}
	}
}
