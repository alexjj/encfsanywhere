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
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;

import de.voot.encfsanywhere.client.HistoryItems;
import de.voot.encfsanywhere.client.presenter.ListPresenter;
import de.voot.encfsanywhere.client.presenter.ListPresenter.ListView;
import de.voot.encfsanywhere.client.view.util.UIUtil;

public class ListViewImpl extends Composite implements ListView {

	private static ListViewImplUiBinder uiBinder = GWT.create(ListViewImplUiBinder.class);

	interface ListViewImplUiBinder extends UiBinder<Widget, ListViewImpl> {
	}

	private ListPresenter presenter;

	@UiField
	Button disconnectButton;
	@UiField
	Button loginButton;
	@UiField
	Button cancelButton;
	@UiField
	PasswordTextBox password;

	@UiField
	FlexTable files;
	@UiField
	FlexTable downloads;
	@UiField
	UListElement breadcrumb;

	public ListViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		password.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					loginClicked();
				} else if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
					cancelClicked();
				}

			}
		});

		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loginClicked();
			}
		});
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				cancelClicked();
			}
		});
		disconnectButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				disconnectClicked();
			}
		});

		password.getElement().setAttribute("placeholder", "Your EncFS Password");
	}

	@Override
	public void setPresenter(ListPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void showList(String path, String[] content, Boolean[] isDirectory) {
		updateBreadcrumb(path);
		updateFiles(content, isDirectory);
	}

	private void updateFiles(String[] content, Boolean[] isDirectory) {
		while (files.getRowCount() > 0) {
			files.removeRow(0);
		}

		for (int i = 0; i < content.length; i++) {
			String filename = content[i];
			Element icon = DOM.createElement("i");
			if (isDirectory[i]) {
				icon.setAttribute("class", "icon-folder-open");
			} else {
				icon.setAttribute("class", "icon-file");
			}
			InlineHTML html = new InlineHTML();
			html.getElement().appendChild(icon);
			files.setWidget(i, 0, html);
			files.setText(i, 1, filename);
		}
	}

	private void updateBreadcrumb(String path) {
		while (breadcrumb.hasChildNodes()) {
			breadcrumb.removeChild(breadcrumb.getLastChild());
		}

		String history = HistoryItems.LIST_PREFIX;
		String[] parts = path.split("/");

		appendToBreadcrumb("root", history + "/", false);

		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];

			if (part.isEmpty()) {
				continue;
			}
			history = history + "/" + part;
			boolean isLast = (i + 1 == parts.length);
			appendToBreadcrumb(part, history, isLast);
		}
	}

	private void appendToBreadcrumb(String text, String history, boolean isLast) {
		Element li = DOM.createElement("li");
		breadcrumb.appendChild(li);

		if (isLast) {
			// last element in breadcrumb
			li.setAttribute("class", "active");
			li.setInnerText(text);
		} else {
			// elements still coming
			li.appendChild(new Hyperlink(text, history).getElement());

			Element divider = DOM.createElement("span");
			divider.setAttribute("class", "divider");
			divider.setInnerText("/");
			li.appendChild(divider);
		}
	}

	@UiHandler("files")
	public void fileTableClicked(ClickEvent clickEvent) {
		Cell cell = files.getCellForEvent(clickEvent);
		if (cell != null) {
			presenter.onRowClicked(cell.getRowIndex());
		}
	}

	@Override
	public void promptForPassword() {
		password.setValue("");
		UIUtil.showPasswordPrompt();
		password.setFocus(true);
	}

	private void loginClicked() {
		UIUtil.hidePasswordPrompt();
		presenter.onPasswordPromptFinished(password.getValue());
	}

	private void cancelClicked() {
		UIUtil.hidePasswordPrompt();
		presenter.onPasswordPromptFinished(null);
	}

	private void disconnectClicked() {
		presenter.onDisconnectClicked();
	}

	@Override
	public void showDownloads(String[] paths, String[] progress) {
		while (downloads.getRowCount() > 0) {
			downloads.removeRow(0);
		}

		for (int i = 0; i < paths.length; i++) {
			String filename = paths[i];
			Element icon = DOM.createElement("i");
			icon.setAttribute("class", "icon-file");
			InlineHTML html = new InlineHTML();
			html.getElement().appendChild(icon);
			downloads.setWidget(i, 0, html);
			downloads.setText(i, 1, filename);
			downloads.setText(i, 2, progress[i]);
		}
	}
}
