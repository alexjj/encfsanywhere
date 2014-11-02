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
import com.google.gwt.user.client.ui.RootPanel;

import de.voot.encfsanywhere.client.event.AlertEvent;
import de.voot.encfsanywhere.client.event.AlertEventHandler;
import de.voot.encfsanywhere.client.gin.Injector;
import de.voot.encfsanywhere.client.gin.InjectorHolder;
import de.voot.encfsanywhere.client.presenter.AlertPresenter;

public class AlertController implements Controller {

	private Injector injector = InjectorHolder.getInstance();
	private HandlerManager eventBus = injector.getHandlerManager();
	private HasWidgets container;

	@Override
	public void init(HasWidgets container) {
		this.container = RootPanel.get("alertContainer");
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(AlertEvent.TYPE, new AlertEventHandler() {
			@Override
			public void showDialog(String caption, String text) {
				AlertPresenter alertPresenter = injector.getAlertPresenter();
				alertPresenter.go(container);
				alertPresenter.show(caption, text);
			}
		});
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {

	}

}
