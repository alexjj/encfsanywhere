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
package de.voot.encfsanywhere.client.controller;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import de.voot.encfsanywhere.client.event.AsyncCallFinishedEvent;
import de.voot.encfsanywhere.client.event.AsyncCallFinishedEventHandler;
import de.voot.encfsanywhere.client.event.AsyncCallStartedEvent;
import de.voot.encfsanywhere.client.event.AsyncCallStartedEventHandler;
import de.voot.encfsanywhere.client.gin.Injector;
import de.voot.encfsanywhere.client.gin.InjectorHolder;
import de.voot.encfsanywhere.client.view.util.UIUtil;

public class UISugarController implements Controller {
	private Injector injector = InjectorHolder.getInstance();
	private HandlerManager eventBus = injector.getHandlerManager();

	private int asyncCallsPlaced = 0;

	@Override
	public void init(HasWidgets container) {
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(AsyncCallStartedEvent.TYPE, new AsyncCallStartedEventHandler() {
			@Override
			public void handle() {
				if (asyncCallsPlaced == 0) {
					UIUtil.showLoadingAnimation();
				}
				asyncCallsPlaced++;
			}
		});

		eventBus.addHandler(AsyncCallFinishedEvent.TYPE, new AsyncCallFinishedEventHandler() {
			@Override
			public void handle() {
				asyncCallsPlaced--;
				if (asyncCallsPlaced == 0) {
					UIUtil.hideLoadingAnimation();
				}
			}
		});

	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
	}
}
