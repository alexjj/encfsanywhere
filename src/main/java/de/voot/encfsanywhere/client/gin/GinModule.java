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
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import de.voot.encfsanywhere.client.controller.AlertController;
import de.voot.encfsanywhere.client.controller.ListController;
import de.voot.encfsanywhere.client.controller.StorageConnectController;
import de.voot.encfsanywhere.client.controller.UISugarController;
import de.voot.encfsanywhere.client.presenter.AlertPresenter;
import de.voot.encfsanywhere.client.presenter.AlertPresenter.AlertWidget;
import de.voot.encfsanywhere.client.presenter.ListPresenter;
import de.voot.encfsanywhere.client.presenter.StorageConnectPresenter;
import de.voot.encfsanywhere.client.presenter.ListPresenter.ListView;
import de.voot.encfsanywhere.client.presenter.StorageConnectPresenter.StorageConnectView;
import de.voot.encfsanywhere.client.view.ListViewImpl;
import de.voot.encfsanywhere.client.view.StorageConnectViewImpl;
import de.voot.encfsanywhere.client.widget.AlertWidgetImpl;

public class GinModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(HandlerManager.class).toProvider(HandlerManagerProvider.class).in(Singleton.class);

		bind(UISugarController.class).in(Singleton.class);

		bind(StorageConnectPresenter.class).in(Singleton.class);
		bind(StorageConnectController.class).in(Singleton.class);
		bind(StorageConnectView.class).to(StorageConnectViewImpl.class).in(Singleton.class);

		bind(ListController.class).in(Singleton.class);
		bind(ListPresenter.class).in(Singleton.class);
		bind(ListView.class).to(ListViewImpl.class).in(Singleton.class);

		bind(AlertWidget.class).to(AlertWidgetImpl.class);
		bind(AlertPresenter.class);
		bind(AlertController.class).in(Singleton.class);
	}
}
