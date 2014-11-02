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
package de.voot.encfsanywhere.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AlertEvent extends GwtEvent<AlertEventHandler> {
	public static Type<AlertEventHandler> TYPE = new Type<AlertEventHandler>();

	private String caption;
	private String text;

	public AlertEvent(String caption, String text) {
		this.caption = caption;
		this.text = text;
	}

	@Override
	public Type<AlertEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AlertEventHandler handler) {
		handler.showDialog(caption, text);
	}

}
