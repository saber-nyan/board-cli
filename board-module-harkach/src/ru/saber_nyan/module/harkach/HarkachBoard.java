package ru.saber_nyan.module.harkach;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.saber_nyan.parser.Board;
import ru.saber_nyan.parser.Thread;
import ru.saber_nyan.utils.Http;

import java.util.ArrayList;

public class HarkachBoard extends Board {

	private static final String API_URL = "https://2ch.hk/%s/%s.json";
	private static final String KEY_BOARD_LONG_NAME = "BoardName";
	private static final String KEY_BOARD_BUMP_LIMIT = "bump_limit";
	private static final String KEY_BOARD_THREADS = "threads";

	public HarkachBoard(String userAgent, String boardName, long boardPage) {
		this.setUserAgent(userAgent);
		this.setName(boardName);
		this.setPage(boardPage);
	}

	@Override
	public void load() throws Exception {
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
			throw http.getException();
		}

		JSONObject jsonObject = new JSONObject(http.getResult());
		this.setLongName(jsonObject.getString(KEY_BOARD_LONG_NAME));
		this.setBumpLimit(jsonObject.getLong(KEY_BOARD_BUMP_LIMIT));

		JSONArray threadsArray = jsonObject.getJSONArray(KEY_BOARD_THREADS);
		long threadsCount = threadsArray.length();

		ArrayList<Thread> threads = new ArrayList<>();

		for (long i = 0; i < threadsCount; i++) {
			Thread newThread = new HarkachThread(this.getName(),
					threadsArray.getJSONObject((int) i).getLong("thread_num"),
					this.getUserAgent());
			threads.add(newThread);
		}

		this.setThreads(threads);
	}
}
