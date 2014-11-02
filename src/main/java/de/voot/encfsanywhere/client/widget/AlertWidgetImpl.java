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
package de.voot.encfsanywhere.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import de.voot.encfsanywhere.client.presenter.AlertPresenter;
import de.voot.encfsanywhere.client.presenter.AlertPresenter.AlertWidget;

public class AlertWidgetImpl extends Composite implements AlertWidget {

	private static AlertWidgetImplUiBinder uiBinder = GWT.create(AlertWidgetImplUiBinder.class);

	interface AlertWidgetImplUiBinder extends UiBinder<Widget, AlertWidgetImpl> {
	}

	private AlertPresenter presenter;

	@UiField
	HeadingElement caption;
	@UiField
	HTML text;
	@UiField
	Button closeButton;

	public AlertWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onHideButtonClicked();
			}
		});
	}

	@Override
	public void setPresenter(AlertPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void show(String caption, String text) {
		this.caption.setInnerText(caption);
		this.text.setHTML(new SafeHtmlBuilder().appendEscapedLines(text).toSafeHtml());
	}

}
