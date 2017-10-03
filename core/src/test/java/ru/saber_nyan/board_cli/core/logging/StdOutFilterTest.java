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
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.spi.FilterReply;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("JavaDoc")
public class StdOutFilterTest {
	@Test
	public void decide() throws Exception {
		StdOutFilter filterNotStarted = new StdOutFilter();
		assertEquals("wrong decision", FilterReply.NEUTRAL, filterNotStarted.decide(132));

		StdOutFilter filter = new StdOutFilter();
		filter.start();

		LoggingEvent event = new LoggingEvent();
		event.setLevel(Level.INFO);
		assertEquals("wrong decision", FilterReply.NEUTRAL, filter.decide(event));

		LoggingEvent event1 = new LoggingEvent();
		event1.setLevel(Level.ERROR);
		assertEquals("wrong decision", FilterReply.DENY, filter.decide(event1));
	}
}
