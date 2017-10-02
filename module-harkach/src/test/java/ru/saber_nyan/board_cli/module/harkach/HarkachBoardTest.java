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
import org.junit.Test;
import ru.saber_nyan.board_cli.module.ImageboardBoard;
import ru.saber_nyan.board_cli.module.ImageboardThread;

import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("JavaDoc")
public class HarkachBoardTest {

	@Test
	public void construct() throws Exception {
		ImageboardBoard board = new HarkachBoard("b", 0, null,
				new OkHttpClient());
		List<ImageboardThread> threads = board.getThreads();
		assertNull("WTF, threads already loaded?!", threads);
	}

	@Test
	public void load() throws Exception {
		ImageboardBoard board = new HarkachBoard("s", 0, null,
				new OkHttpClient());
		board.load();
		List<ImageboardThread> threads = board.getThreads();
		assertNotNull("threads list is not loaded...", threads);
		assertTrue("the first thread has an invalid number", threads.get(0).getNumber() > 0);

		ImageboardBoard board1 = new HarkachBoard("s", 1, "Loled!",
				new OkHttpClient());
		board1.load();
	}

	@Test(expected = IllegalStateException.class)
	public void load_twice() throws Exception {
		ImageboardBoard board = new HarkachBoard("a", 0, null,
				new OkHttpClient());
		board.load();
		board.load();
	}
}
