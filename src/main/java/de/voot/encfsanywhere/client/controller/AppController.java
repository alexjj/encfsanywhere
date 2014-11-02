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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import de.voot.encfsanywhere.client.HistoryItems;
import de.voot.encfsanywhere.client.gin.Injector;
import de.voot.encfsanywhere.client.gin.InjectorHolder;

public class AppController implements Controller {

	private static final Logger LOG = Logger.getLogger("de.voot.encfsanywhere.client.controller.AppController");

	private Injector injector = InjectorHolder.getInstance();
	private HasWidgets container;

	@Override
	public void init(HasWidgets container) {
		this.container = container;

		bind();
		initControllers();

		if ("".equals(History.getToken())) {
			History.newItem(HistoryItems.STORAGE_AUTO_CONNECT);
		} else {
			History.fireCurrentHistoryState();
		}
	}

	private void bind() {
		History.addValueChangeHandler(this);
	}

	private void initControllers() {
		LOG.log(Level.INFO, "Initializing controllers");

		Controller[] controllers = new Controller[] { injector.getAlertController(), injector.getStorageConnectController(), injector.getListController(),
				injector.getUiSugarController() };
		for (Controller controller : controllers) {
			controller.init(container);
		}
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {

	}

}
