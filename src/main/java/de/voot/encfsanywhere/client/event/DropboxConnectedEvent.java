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

import de.voot.dropboxgwt.client.DropboxWrapper;

public class DropboxConnectedEvent extends GwtEvent<DropboxConnectedEventHandler> {
	public static Type<DropboxConnectedEventHandler> TYPE = new Type<DropboxConnectedEventHandler>();

	private DropboxWrapper dropboxWrapper;

	public DropboxConnectedEvent(DropboxWrapper dropboxWrapper) {
		this.dropboxWrapper = dropboxWrapper;
	}

	@Override
	public Type<DropboxConnectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DropboxConnectedEventHandler handler) {
		handler.onDropboxConnected(dropboxWrapper);
	}

}
