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

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

@SuppressWarnings("JavaDoc")
public class PairTest {

	private static final String FIRST_TEST = "test1";
	private static final String SECOND_TEST = "test2";
	private static final String FORMAT_TEST = "%s - %s";

	private Pair<String, String> pair;

	@Before
	public void setUp() throws Exception {
		pair = new Pair<>(FORMAT_TEST, FIRST_TEST, SECOND_TEST);
	}

	@Test
	public void construct() throws Exception {
		Pair<String, Integer> pair = new Pair<>("%s:%s", null, null);
		assertEquals("strings don\'t match", "null:null", pair.toString());
	}

	@Test
	public void getFirst() throws Exception {
		String first = pair.getFirst();
		assertEquals("first don\'t equal", FIRST_TEST, first);
	}

	@Test
	public void getSecond() throws Exception {
		String second = pair.getSecond();
		assertEquals("second don\'t equal", SECOND_TEST, second);
	}

	@Test
	public void hashCode_test() throws Exception {
		int expectedHash = Objects.hash(FIRST_TEST, SECOND_TEST);
		assertEquals("hashes don\'t match", expectedHash, pair.hashCode());
	}

	@SuppressWarnings({"EqualsWithItself", "EqualsBetweenInconvertibleTypes"})
	@Test
	public void equals_test() throws Exception {
		//noinspection LiteralAsArgToStringEquals
		assertFalse("equals with other type", pair.equals("lol\'d"));
		assertTrue("same object not equal", pair.equals(pair));

		Pair<String, String> otherPair = new Pair<>("doesn\'t matter",
				FIRST_TEST, "otherStr");
		assertFalse("wrong equals", pair.equals(otherPair));

		Pair<String, String> otherPair1 = new Pair<>("...",
				"other first", SECOND_TEST);
		assertFalse("wrong equals", pair.equals(otherPair1));
	}
}
