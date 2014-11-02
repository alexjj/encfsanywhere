/*
  	Copyright (C) 2013 Marco Schulte

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.voot.encfsanywhere.client.util;

import de.voot.encfsgwt.shared.jre.InputStream;

public class BrowserUtil {

	public static native void download(InputStream inputStream, String filename) /*-{
		var bufferSize = 1024 * 1024;
		var buffers = [];

		var finished = false;
		while (!finished) {
			var buffer = new ArrayBuffer(bufferSize);
			var view = new Uint8Array(buffer);

			var bytesRead = inputStream.@de.voot.encfsgwt.shared.jre.InputStream::read(Lcom/google/gwt/core/client/JsArrayInteger;)(view);

			if (bytesRead == -1) {
				finished = true;
			} else if (bytesRead != view.byteLength) {
				view = view.subarray(0, bytesRead);
				finished = true;
			}

			if (bytesRead > -1) {
				buffers.push(view);
			}

		}

		var blob = new Blob(buffers);
		$wnd.saveAs(blob, filename);
	}-*/;
}
