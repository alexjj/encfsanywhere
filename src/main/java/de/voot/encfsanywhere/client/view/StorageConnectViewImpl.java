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
package de.voot.encfsanywhere.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.voot.encfsanywhere.client.presenter.StorageConnectPresenter;
import de.voot.encfsanywhere.client.presenter.StorageConnectPresenter.StorageConnectView;
import de.voot.encfsanywhere.client.view.util.FlatUIHelper;
import de.voot.encfsanywhere.client.view.util.UIUtil;

public class StorageConnectViewImpl extends Composite implements StorageConnectView {

	private static StorageConnectViewUiBinder uiBinder = GWT.create(StorageConnectViewUiBinder.class);
	@UiField
	CheckBox stayConnectedCheckbox;
	@UiField
	CheckBox provideAPIKey;
	@UiField
	Button connectDropboxButton;
	@UiField
	TextBox dropboxApiKey;

	private StorageConnectPresenter presenter;

	interface StorageConnectViewUiBinder extends UiBinder<Widget, StorageConnectViewImpl> {
	}

	public StorageConnectViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		FlatUIHelper.initCheckboxOrRadio(provideAPIKey.getElement());
		FlatUIHelper.initCheckboxOrRadio(stayConnectedCheckbox.getElement());

		provideAPIKey.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if(provideAPIKey.getValue()){
					UIUtil.showProvdeDBApiKeyInstructions();
				}else{
					UIUtil.hideProvdeDBApiKeyInstructions();
				}
			}
		});
	}

	@Override
	public void setPresenter(StorageConnectPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasValue<Boolean> rememberUserChecked() {
		return stayConnectedCheckbox;
	}

	@Override
	public HasValue<Boolean> customApiKeyChecked() {
		return provideAPIKey;
	}

	@Override
	public HasValue<String> dropboxApiKey() {
		return dropboxApiKey;
	}

	@UiHandler("connectDropboxButton")
	void onConnectDropboxButtonClick(ClickEvent event) {
		presenter.onDropboxConnectButtonClicked();
	}
}
