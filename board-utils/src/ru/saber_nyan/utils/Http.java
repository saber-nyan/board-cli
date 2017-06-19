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

package ru.saber_nyan.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {

	private boolean isError = false;
	private String result;
	private Exception exception;

	public boolean isError() {
		return isError;
	}

	public String getResult() {
		return result;
	}

	public Exception getException() {
		return exception;
	}


	public void sendGet(String urlStr, String userAgent) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", userAgent);
			int responseCode = connection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Response code is not \"HTTP/1.0 200 OK\" (" + responseCode + ")!");
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			this.result = response.toString();
		} catch (RuntimeException | IOException e) {
			this.exception = e;
			this.isError = true;
		}
	}

	public void sendGetBinaryToFile(String urlStr, String filePath) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Response code is not \"HTTP/1.0 200 OK\" (" + responseCode + ")!");
			}

			InputStream input = connection.getInputStream();
			byte[] buffer = new byte[4096];
			int tmp;

			OutputStream output = new FileOutputStream(filePath);
			while ((tmp = input.read(buffer)) != -1) {
				output.write(buffer, 0, tmp);
			}

			output.close();
		} catch (RuntimeException | IOException e) {
			this.exception = e;
			this.isError = true;
		}
	}
}
