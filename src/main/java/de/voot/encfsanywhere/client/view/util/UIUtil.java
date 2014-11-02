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

public class UIUtil {

	public static final native void showLoadingAnimation() /*-{
		$wnd.$("#loadingBar").show();
	}-*/;

	public static final native void hideLoadingAnimation() /*-{
		$wnd.$("#loadingBar").hide();
	}-*/;

	public static final native void showKeyDerivationOverlay() /*-{
		$wnd.$("#derivatingKeyOverlay").show();
	}-*/;

	public static final native void hideKeyDerivationOverlay() /*-{
		$wnd.$("#derivatingKeyOverlay").hide();
	}-*/;

	public static final native void showPasswordPrompt() /*-{
		$wnd.$("#passwordEntryPrompt").show();
	}-*/;

	public static final native void hidePasswordPrompt() /*-{
		$wnd.$("#passwordEntryPrompt").hide();
	}-*/;

	public static final native void showProvdeDBApiKeyInstructions() /*-{
		$wnd.$("#ownDropboxKeyInstructions").slideDown();
	}-*/;

	public static final native void hideProvdeDBApiKeyInstructions() /*-{
		$wnd.$("#ownDropboxKeyInstructions").slideUp();
	}-*/;
}
