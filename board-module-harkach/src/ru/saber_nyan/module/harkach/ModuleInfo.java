/*******************************************************************************
 * Copyright (c) 2017 saber-nyan
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 ******************************************************************************/

package ru.saber_nyan.module.harkach;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ModuleInfo extends ru.saber_nyan.parser.ModuleInfo {
	public static final String moduleName = "Харкач (2ch.hk)";
	public static final String imageboardName = HarkachImageboard.class.getCanonicalName();
	public static final String boardName = HarkachBoard.class.getCanonicalName();
	public static final String threadName = HarkachThread.class.getCanonicalName();
	public static final String postName = HarkachPost.class.getCanonicalName();
	public static final String fileName = HarkachFile.class.getCanonicalName();
}
