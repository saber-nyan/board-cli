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
import ru.saber_nyan.board_cli.module.ImageboardImageboard;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("JavaDoc")
public class HarkachImageboardTest {

	@Test
	public void construct() throws Exception {
		ImageboardImageboard imageboard = new HarkachImageboard(new OkHttpClient());
		List<ImageboardBoard> boards = imageboard.getBoards();
		assertNotNull("boards list is not initialized", boards);
		assertFalse("boards list is empty", boards.isEmpty());
	}
}
