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

package ru.saber_nyan.board_cli.module.harkach;

import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import ru.saber_nyan.board_cli.module.ImageboardFile;
import ru.saber_nyan.board_cli.module.ImageboardPost;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SuppressWarnings("JavaDoc")
public class HarkachPostTest {

	private static final String COMMENT = "Some\nMultiline\nComment\n\t\t\tJust 4 \"LulZ\"\t" +
			"Triforce!" +
			"\u00a0\u25b2" +
			"\u25b2\u00a0\u25b2";
	private static final long NUMBER = 13371337L;
	private static final String DATE = "11/09/01 Вт 08:46:00";
	private static final String FILE_NAME = "some_file.webm";
	private static final String FILE_URL = "/res/some_file.webm";

	private static final Map<String, Object> inFile = new HashMap<String, Object>() {{
		put("name", FILE_NAME);
		put("path", FILE_URL);
	}};

	private static final List<JSONObject> inFiles = Collections.singletonList(new JSONObject(inFile));
	private static final Map<String, Object> inMap = new HashMap<String, Object>() {{
		put("num", NUMBER);
		put("banned", 1);
		put("op", 1);
		put("comment", COMMENT);
		put("date", DATE);
		put("files", new JSONArray(inFiles));
	}};

	private static final Map<String, Object> inMap1 = new HashMap<String, Object>() {{
		put("num", NUMBER);
		put("banned", 1);
		put("op", 1);
		put("comment", COMMENT);
		put("date", DATE);
	}};

	@Test
	public void construct() throws Exception {
		JSONObject input = new JSONObject(inMap);
		ImageboardPost post = new HarkachPost(input, new OkHttpClient());
		assertEquals("numbers don\'t match", NUMBER, post.getNumber());
		assertEquals("comments don\'t match", COMMENT, post.getComment());
		assertEquals("dates don\'t match", DATE, post.getDate());
		assertEquals("post isn\'t banned", post.isBanned(), true);
		assertEquals("post isn\'t OP", post.isOp(), true);

		List<ImageboardFile> files = post.getFiles();
		assertNotNull("files list is null", files);
		assertFalse("files list is empty", files.isEmpty());

		ImageboardFile file = files.get(0);
		assertEquals("file names don\'t match", FILE_NAME, file.getFilename());
		assertEquals("file URLs don\'t match", "https://2ch.hk" + FILE_URL, file.getUrl());

		JSONObject input1 = new JSONObject(inMap1);
		ImageboardPost post1 = new HarkachPost(input1, new OkHttpClient());
		assertNull("file list isn\'t null", post1.getFiles());
	}
}
