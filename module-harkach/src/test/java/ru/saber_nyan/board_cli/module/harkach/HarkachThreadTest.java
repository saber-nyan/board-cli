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
import ru.saber_nyan.board_cli.module.ImageboardPost;
import ru.saber_nyan.board_cli.module.ImageboardThread;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SuppressWarnings("JavaDoc")
public class HarkachThreadTest {

	@Test
	public void construct() throws Exception {
		// Тред об API на /abu/, не удалят ведь?
		ImageboardThread thread = new HarkachThread("abu", null,
				null, 42375, new OkHttpClient());
		List<ImageboardPost> posts = thread.getPosts();
		assertNull("WTF, posts already loaded?!", posts);
	}

	@Test
	public void load() throws Exception {
		ImageboardThread thread = new HarkachThread("abu", null,
				null, 42375, new OkHttpClient());
		thread.load();
		List<ImageboardPost> posts = thread.getPosts();
		assertNotNull("posts list is not loaded...", posts);
		ImageboardPost firstPost = posts.get(0);
		assertNotNull("first post has no comment", firstPost.getComment());
		assertNotNull("first post has no date", firstPost.getDate());
	}

	@Test(expected = IllegalStateException.class)
	public void load_twice() throws Exception {
		ImageboardThread thread = new HarkachThread("abu", null,
				null, 42375, new OkHttpClient());
		thread.load();
		thread.load();
	}
}
