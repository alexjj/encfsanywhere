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

import com.google.gwt.core.client.Callback;

import de.voot.encfsanywhere.client.event.AsyncCallFinishedEvent;
import de.voot.encfsanywhere.client.event.AsyncCallStartedEvent;
import de.voot.encfsanywhere.client.gin.InjectorHolder;

public class Async {
	/**
	 * Automatic firing of AsyncCallStartedEvent and AsyncCallFinishedEvent
	 * 
	 * @param callback
	 * @return
	 */
	public static <T, F> Callback<T, F> wrap(final Callback<T, F> callback) {
		Callback<T, F> result = new Callback<T, F>() {
			@Override
			public void onFailure(F reason) {
				InjectorHolder.getInstance().getHandlerManager().fireEvent(new AsyncCallFinishedEvent());
				callback.onFailure(reason);
			}

			@Override
			public void onSuccess(T result) {
				InjectorHolder.getInstance().getHandlerManager().fireEvent(new AsyncCallFinishedEvent());
				callback.onSuccess(result);
			}
		};

		InjectorHolder.getInstance().getHandlerManager().fireEvent(new AsyncCallStartedEvent());
		return result;
	}
}
