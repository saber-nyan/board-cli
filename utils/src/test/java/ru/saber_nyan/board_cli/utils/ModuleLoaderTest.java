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

package ru.saber_nyan.board_cli.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("JavaDoc")
public class ModuleLoaderTest {

	private static final String DOWNLOAD_URL = "https://github.com/saber-nyan/board-cli/releases/download/" +
			"v0.0.1b/board-module-harkach.jar";

	private static final String FILE_PATH = "." + File.separator + "test.jar";

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
	public void loadClassesFromJar() throws Exception {
		List<Class> classes = ModuleLoader.loadClassesFromJar(FILE_PATH, "saber_nyan");
		assertNotNull("classes list is null", classes);
		assertFalse("classes list is empty", classes.isEmpty());

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
