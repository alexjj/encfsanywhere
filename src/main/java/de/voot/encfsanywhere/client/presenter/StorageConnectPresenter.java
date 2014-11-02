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
package de.voot.encfsanywhere.client.presenter;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;

import de.voot.encfsanywhere.client.gin.Injector;
import de.voot.encfsanywhere.client.gin.InjectorHolder;
import de.voot.encfsanywhere.client.view.View;

public class StorageConnectPresenter implements Presenter {

	public interface StorageConnectView extends View<StorageConnectPresenter> {
		public HasValue<Boolean> rememberUserChecked();
		public HasValue<Boolean> customApiKeyChecked();
		public HasValue<String> dropboxApiKey();
	}

	private final Injector injector = InjectorHolder.getInstance();
	private final StorageConnectView view = injector.getStorageConnectView();

	public StorageConnectPresenter() {
		view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	public void onDropboxConnectButtonClicked() {
		Boolean rememberUser = view.rememberUserChecked().getValue();

		if(view.customApiKeyChecked().getValue()){
			injector.getStorageConnectController().connectToDropbox(rememberUser, view.dropboxApiKey().getValue());
		}else{
			injector.getStorageConnectController().connectToDropbox(rememberUser);
		}
	}

}
