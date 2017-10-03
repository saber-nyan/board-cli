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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("JavaDoc")
public class HttpExceptionTest {
	@Test(expected = HttpException.class)
	public void getResponseCode() throws Exception {
		HttpException exception = new HttpException("fail!", 404);
		HttpException exception1 = new HttpException(500);
		assertEquals("responseCode not equal", 404, exception.getResponseCode());
		assertEquals("responseCode not equal", 500, exception1.getResponseCode());
		throw exception;
	}
}
