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
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;
import ru.saber_nyan.board_cli.module.ImageboardFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SuppressWarnings("JavaDoc")
public class HarkachFileTest {

	private final List<String> downloadPaths = new ArrayList<>(3);

	private static final String FILE_NAME = "14433751460020.jpg";
	private static final String FILE_URL = "/abu/src/42375/14433751460020.jpg";

	private static final Map<String, Object> inFile = new HashMap<String, Object>() {{
		put("name", FILE_NAME);
		put("path", FILE_URL);
	}};

	private static final Map<String, Object> inFile1 = new HashMap<String, Object>() {{
		put("fullname", FILE_NAME);
		put("path", FILE_URL);
	}};

	@Test
	public void construct() throws Exception {
		JSONObject input = new JSONObject(inFile);
		ImageboardFile file = new HarkachFile(input, new OkHttpClient());
		assertEquals("file names don\'t match", FILE_NAME, file.getFilename());
		assertEquals("file URLs don\'t match", "https://2ch.hk" + FILE_URL, file.getUrl());
		JSONObject input1 = new JSONObject(inFile1);
		ImageboardFile file1 = new HarkachFile(input1, new OkHttpClient());
		assertEquals("file names don\'t match", FILE_NAME, file1.getFilename());
	}

	@Test
	public void load() throws Exception {
		JSONObject input = new JSONObject(inFile);
		ImageboardFile file = new HarkachFile(input, new OkHttpClient());
		String downloadedPath = file.load("." + File.separator);
		downloadPaths.add(downloadedPath);
		assertFalse("downloaded file path is empty", downloadedPath.isEmpty());
		File downloadedFile = new File(downloadedPath);
		assertTrue("file \"" + downloadedPath + "\" doesn\'t exist", downloadedFile.exists());
		assertFalse("dir?!", downloadedFile.isDirectory());
	}

	@Test(expected = IllegalStateException.class)
	public void load_twice() throws Exception {
		JSONObject input = new JSONObject(inFile);
		ImageboardFile file = new HarkachFile(input, new OkHttpClient());
		String path1 = file.load("." + File.separator);
		downloadPaths.add(path1);
		String path2 = file.load("." + File.separator);
		downloadPaths.add(path2);
	}

	/**
	 * Убираем рабочее пространство после себя...
	 */
	@After
	public void tearDown() {
		downloadPaths.forEach(path -> {
			try {
				File fileToDelete = new File(path);
				System.out.println("delete \'" + fileToDelete.getAbsolutePath() + '\'');
				//noinspection ResultOfMethodCallIgnored
				fileToDelete.delete();
			} catch (Exception ignored) {
			}
		});
	}
}
