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
package de.voot.encfsanywhere.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.voot.encfsanywhere.client.controller.AppController;
import de.voot.encfsanywhere.client.logging.AlertEventLogHandler;

public class EncfsAnywhere implements EntryPoint {

	private static final Logger LOG = Logger.getLogger("de.voot.encfsanywhere.client.EncfsAnywhere");

	@Override
	public void onModuleLoad() {
		Logger rootLogger = Logger.getLogger("");
		rootLogger.addHandler(new AlertEventLogHandler());

		LOG.info("EncfsAnywhere loaded. Starting AppController.");

		AppController appController = new AppController();
		appController.init(RootPanel.get("content"));
	}

}
