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

/**
 * Module for <i>2ch.hk</i> (<i>2ch.pm</i>).
 * <p>
 * API reference: <a href="https://2ch.hk/abu/res/42375.html">link</a>
 *
 * @author saber-nyan
 */
@SuppressWarnings("unused")
public class ModuleInfo extends ru.saber_nyan.board_cli.module.ModuleInfo {
	/**
	 * The display name of the module.
	 */
	public static final String moduleName = "[2ch.hk] Харкач";
	/**
	 * Class name: {@link HarkachImageboard}.
	 */
	public static final String imageboardName = HarkachImageboard.class.getCanonicalName();
	/**
	 * Class name: {@link HarkachBoard}.
	 */
	public static final String boardName = HarkachBoard.class.getCanonicalName();
	/**
	 * Class name: {@link HarkachThread}.
	 */
	public static final String threadName = HarkachThread.class.getCanonicalName();
	/**
	 * Class name: {@link HarkachPost}.
	 */
	public static final String postName = HarkachPost.class.getCanonicalName();
	/**
	 * Class name: {@link HarkachFile}.
	 */
	public static final String fileName = HarkachFile.class.getCanonicalName();
}
