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

import ch.qos.logback.core.spi.FilterReply;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("JavaDoc")
public class AnyFilterTest {
	@Test
	public void decide() throws Exception {
		AnyFilter filter = new AnyFilter();
		FilterReply reply = filter.decide("lol");
		assertEquals("wrong decision", FilterReply.NEUTRAL, reply);
		filter.start();
		FilterReply reply1 = filter.decide("lold");
		assertEquals("wrong decision", FilterReply.ACCEPT, reply1);
	}
}
