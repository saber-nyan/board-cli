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

import java.io.IOException;

/**
 * Enhanced {@link IOException}: it contains {@link #responseCode}
 * from server!
 */
public class HttpException extends IOException {

	/**
	 * Response code from server.
	 */
	private final int responseCode;

	/**
	 * Constructs an {@code HttpException} with specified body.
	 *
	 * @param responseBody the detail responseBody (which is saved for later retrieval
	 *                     by the {@link #getMessage()} method)
	 * @param responseCode server response code
	 */
	public HttpException(String responseBody, int responseCode) {
		super(responseBody);
		this.responseCode = responseCode;
	}

	/**
	 * Constructs an {@code HttpException} with specified response code.
	 *
	 * @param responseCode server response code
	 */
	public HttpException(int responseCode) {
		super();
		this.responseCode = responseCode;
	}

	/**
	 * @return {@link #responseCode}
	 */
	public int getResponseCode() {
		return responseCode;
	}
}
