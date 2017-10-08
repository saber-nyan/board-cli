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

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

/**
 * Various utilities.
 */
public final class Utils {
	private Utils() {
		super();
	}

	/**
	 * Display dialog with error description.
	 *
	 * @param logger  logger instance
	 * @param gui     Lanterna gui
	 * @param message error description
	 * @param error   (optional) {@link Throwable} object
	 */
	public static void reportError(@NotNull Logger logger,
								   @NotNull WindowBasedTextGUI gui,
								   @NotNull String message,
								   @Nullable Throwable error) {
		MessageDialog.showMessageDialog(gui, "An error has occurred", message);
		if (error != null) {
			logger.error("a non-critical error has occurred:", error);
		} else {
			logger.error("a non-critical error has occurred: {}", message);
		}
	}
}
