package ru.saber_nyan.module.harkach;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.saber_nyan.parser.Board;
import ru.saber_nyan.parser.Thread;
import ru.saber_nyan.utils.Http;

import java.util.ArrayList;

public class HarkachBoard extends Board {

	private static final String API_URL = "https://2ch.hk/%s/%s.json";
	private static final String KEY_BOARD_BUMP_LIMIT = "bump_limit";
	private static final String KEY_BOARD_THREADS = "threads";
	private static final String KEY_BOARD_THREAD_NUM = "thread_num";

	public HarkachBoard(String userAgent, String boardName, String boardDesc, long boardPage) {
		this.setUserAgent(userAgent);
		this.setName(boardName);
		this.setLongName(boardDesc);
		this.setPage(boardPage);
	}

	@Override
	public void load() throws RuntimeException {
		Http http = new Http();
		String pageStr;
		long page = this.getPage();
		if (page == 0) {
			pageStr = "index";
		} else {
			pageStr = String.valueOf(page);
		}
		String url = String.format(API_URL, this.getName(), pageStr);
		http.sendGet(url, this.getUserAgent());

		if (http.isError()) {
			throw new RuntimeException("(Board loading) Error connecting to server!",
					http.getException());
		}

		JSONObject jsonObject = new JSONObject(http.getResult());
		this.setBumpLimit(jsonObject.optLong(KEY_BOARD_BUMP_LIMIT));

		JSONArray threadsArray = jsonObject.optJSONArray(KEY_BOARD_THREADS);
		long threadsCount = threadsArray.length();

		ArrayList<Thread> threads = new ArrayList<>();

		for (long i = 0; i < threadsCount; i++) {
			Thread newThread = new HarkachThread(this.getName(),
					threadsArray.getJSONObject((int) i).getLong(KEY_BOARD_THREAD_NUM),
					this.getUserAgent());
			threads.add(newThread);
		}

		this.setThreads(threads);
	}
}
