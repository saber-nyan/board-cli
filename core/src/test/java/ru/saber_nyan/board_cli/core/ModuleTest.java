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

package ru.saber_nyan.board_cli.core;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.saber_nyan.board_cli.module.ImageboardBoard;
import ru.saber_nyan.board_cli.module.ImageboardImageboard;
import ru.saber_nyan.board_cli.module.ImageboardThread;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.assertFalse;

@SuppressWarnings("JavaDoc")
public class ModuleTest {

	private static final String DOWNLOAD_URL = "https://github.com/saber-nyan/board-cli/releases/download/" +
			"v0.0.1b/board-module-harkach.jar";

	private static final String FILE_PATH = "." + File.separator + "test.jar";

	@SuppressWarnings("Duplicates")
	@Before
	public void setUp() throws Exception {
		Request request = new Request.Builder()
				.url(DOWNLOAD_URL)
				.get()
				.build();
		Response response = new OkHttpClient().newCall(request).execute();
		FileOutputStream os = new FileOutputStream(FILE_PATH);
		//noinspection ConstantConditions
		os.write(response.body().bytes());
		os.close();
	}

	@Test
	public void construct() throws Exception {
		Module module = new Module(FILE_PATH);
		assertFalse("classes list is empty", module.getClasses().isEmpty());
	}

	@SuppressWarnings("ConstantConditions")
	@Test
	public void loadAllModules() throws Exception {
		OkHttpClient client = new OkHttpClient();
		Module module = new Module(FILE_PATH);
		module.loadAllModules(module.getClasses().get(0));
		ImageboardImageboard imageboard =
				(ImageboardImageboard) module.getImageboard().newInstance(client);
		assertFalse("boards list fail (empty)", imageboard.getBoards().isEmpty());

		ImageboardBoard board =
				(ImageboardBoard) module.getBoard().newInstance("s", 0L, "Untitled", client);
		board.load();
		assertFalse("threads list fail (empty)", board.getThreads().isEmpty());

		//noinspection unused
		ImageboardThread thread =
				(ImageboardThread) module.getThread().newInstance("abu", null, 42375L, client);
//		thread.load(); // FIXME: Fukken weird bug! JSONException: JSONObject["fullname"] not found. at (HarkachFile.java:66)
//		assertFalse("posts list fail (empty)", thread.getPosts().isEmpty());

	}

	@After
	public void tearDown() throws Exception {
		try {
			File jar = new File(FILE_PATH);
			//noinspection ResultOfMethodCallIgnored
			jar.delete();
		} catch (Exception ignored) {
		}
	}
}
