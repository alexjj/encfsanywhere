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
package de.voot.encfsanywhere.client.view.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class FlatUIHelper {

	public static void initCheckboxOrRadio(Element element) {
		doInitCheckboxOrRadio(element);
	}

	private static native void doInitCheckboxOrRadio(JavaScriptObject wrap) /*-{
		// rearrange tags to confirm with flat-ui
		wrap = $wnd.$(wrap);
		var inp = wrap.children("input");
		var lbl = wrap.children("label");
		lbl.append(inp);
		lbl.insertBefore(lbl.parent());
		lbl.addClass("checkbox");
		wrap.remove();

		// First let's prepend icons (needed for effects)
		lbl
				.prepend("<span class='icon'></span><span class='icon-to-fade'></span>");

		lbl.click(function() {
			$wnd.updateCheckboxRadioLabels();
		});
		$wnd.updateCheckboxRadioLabels();
	}-*/;
}
