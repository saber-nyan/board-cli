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

package ru.saber_nyan.board_cli.module;

/**
 * Provides information about the module to
 * work with {@link sun.reflect.Reflection} API.
 */
@SuppressWarnings("unused")
public abstract class ModuleInfo {
	/**
	 * The display name of the module.
	 */
	public static final String moduleName = null;
	/**
	 * Class name: {@link ImageboardImageboard}.
	 */
	public static final String imageboardName = "imageboardName";
	/**
	 * Class name: {@link ImageboardBoard}.
	 */
	public static final String boardName = "boardName";
	/**
	 * Class name: {@link ImageboardThread}.
	 */
	public static final String threadName = "threadName";
	/**
	 * Class name: {@link ImageboardPost}.
	 */
	public static final String postName = "postName";
	/**
	 * Class name: {@link ImageboardFile}.
	 */
	public static final String fileName = "fileName";
}
