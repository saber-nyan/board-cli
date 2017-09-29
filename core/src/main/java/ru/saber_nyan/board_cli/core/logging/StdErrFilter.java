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

package ru.saber_nyan.board_cli.core.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * Pipe all {@link Level#ERROR} and {@link Level#WARN} to {@code STDERR}.
 */
public class StdErrFilter extends AbstractMatcherFilter {
	/**
	 * If the decision is <code>{@link FilterReply#DENY}</code>, then the rawEvent will be
	 * dropped. If the decision is <code>{@link FilterReply#NEUTRAL}</code>, then the next
	 * filter, if any, will be invoked. If the decision is
	 * <code>{@link FilterReply#ACCEPT}</code> then the rawEvent will be logged without
	 * consulting with other filters in the chain.
	 *
	 * @param rawEvent The rawEvent to decide upon.
	 */
	@Override
	public FilterReply decide(Object rawEvent) {
		if (!isStarted()) {
			return FilterReply.NEUTRAL;
		}

		Level msgLevel = ((ILoggingEvent) rawEvent).getLevel();

		if (msgLevel == Level.ERROR || msgLevel == Level.WARN) {
			return FilterReply.NEUTRAL;
		} else {
			return FilterReply.DENY;
		}
	}
}
