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
package de.voot.encfsanywhere.client.gin;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import de.voot.encfsanywhere.client.controller.AlertController;
import de.voot.encfsanywhere.client.controller.ListController;
import de.voot.encfsanywhere.client.controller.StorageConnectController;
import de.voot.encfsanywhere.client.controller.UISugarController;
import de.voot.encfsanywhere.client.presenter.AlertPresenter;
import de.voot.encfsanywhere.client.presenter.ListPresenter;
import de.voot.encfsanywhere.client.presenter.StorageConnectPresenter;
import de.voot.encfsanywhere.client.presenter.AlertPresenter.AlertWidget;
import de.voot.encfsanywhere.client.presenter.ListPresenter.ListView;
import de.voot.encfsanywhere.client.presenter.StorageConnectPresenter.StorageConnectView;

@GinModules(GinModule.class)
public interface Injector extends Ginjector {

	public HandlerManager getHandlerManager();

	public UISugarController getUiSugarController();

	public StorageConnectPresenter getStorageConnectPresenter();

	public StorageConnectView getStorageConnectView();

	public StorageConnectController getStorageConnectController();

	public ListController getListController();

	public ListView getListView();

	public ListPresenter getListPresenter();

	public AlertWidget getAlertWidget();

	public AlertController getAlertController();

	public AlertPresenter getAlertPresenter();

}
