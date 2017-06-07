package ru.saber_nyan.module.harkach;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.saber_nyan.parser.Post;
import ru.saber_nyan.parser.Thread;
import ru.saber_nyan.utils.Http;

import java.util.ArrayList;

public class HarkachThread extends Thread {

	private static final String API_URL = "https://2ch.hk/%s/res/%s.json";
	private static final String KEY_THREADS = "threads";
	private static final String KEY_THREAD_TITLE = "title";
	private static final String KEY_THREAD_POST_COUNT = "posts_count";
	private static final String KEY_THREAD_FILE_COUNT = "files_count";
	private static final String KEY_THREAD_POSTS = "posts";

	public HarkachThread(String boardName, long threadNum, String userAgent) {
		this.setBoardName(boardName);
		this.setNumber(threadNum);
		this.setUserAgent(userAgent);
	}

	@Override
	public void load() throws Exception {
		Http http = new Http();
		String url = String.format(API_URL, this.getBoardName(), String.valueOf(this.getNumber()));
		http.sendGet(url, this.getUserAgent());

		if (http.isError()) {
			throw http.getException();
		}

		JSONObject jsonObject = new JSONObject(http.getResult());
		this.setTitle(jsonObject.getString(KEY_THREAD_TITLE));
		this.setPostCount(jsonObject.getLong(KEY_THREAD_POST_COUNT));
		this.setFileCount(jsonObject.getLong(KEY_THREAD_FILE_COUNT));

		JSONObject thisThread = jsonObject.getJSONArray(KEY_THREADS).getJSONObject(0);
		JSONArray postsArray = thisThread.getJSONArray(KEY_THREAD_POSTS);
		long postsCount = postsArray.length();

		ArrayList<Post> posts = new ArrayList<>();

		for (long i = 0; i < postsCount; i++) {
			Post newPost = new HarkachPost(postsArray.optJSONObject((int) i));
			posts.add(newPost);
		}

		this.setPosts(posts);
	}
}
