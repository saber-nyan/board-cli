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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Container to ease passing around a tuple of two objects.
 * This object provides a sensible implementation of equals(),
 * returning true if equals() is true on each of the contained objects.
 *
 * @param <FIRST>  type of first member
 * @param <SECOND> type of second member
 */
public class Pair<FIRST, SECOND> {
	/**
	 * First object in the {@link Pair}.
	 */
	@Nullable
	private final FIRST first;

	/**
	 * Second object in the {@link Pair}.
	 */
	@Nullable
	private final SECOND second;

	/**
	 * Template string to use in {@link #toString()} method.
	 * <p>
	 * The string must contain exactly two {@code %s}.
	 */
	@NotNull
	private final String toStringFormat;

	/**
	 * @param toStringFormat {@link #toStringFormat}
	 * @param first          {@link #first}
	 * @param second         {@link #second}
	 */
	public Pair(@NotNull String toStringFormat, @Nullable FIRST first,
				@Nullable SECOND second) {
		super();
		this.toStringFormat = toStringFormat;
		this.first = first;
		this.second = second;
	}

	/**
	 * @return {@link #first}
	 */
	@Nullable
	public FIRST getFirst() {
		return first;
	}

	/**
	 * @return {@link #second}
	 */
	@Nullable
	public SECOND getSecond() {
		return second;
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	@Override
	public boolean equals(Object otherRaw) {
		if (otherRaw instanceof Pair) {
			Pair other = (Pair) otherRaw;

			boolean firstEquals = (this.first == other.first ||
					Objects.equals(this.first, other.first));
			boolean secondEquals = (this.second == other.second ||
					Objects.equals(this.first, other.first));
			return firstEquals && secondEquals;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
//		//noinspection StringBufferReplaceableByString slower!
//		return new StringBuilder("(")
//				.append(first)
//				.append(", ")
//				.append(second)
//				.append(")")
//				.toString();
		return String.format(toStringFormat, first, second);
	}
}
