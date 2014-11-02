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

import com.google.gwt.user.client.ui.HasWidgets;
import de.voot.encfsanywhere.client.gin.InjectorHolder;
import de.voot.encfsanywhere.client.view.View;

public class AlertPresenter implements Presenter {

	public interface AlertWidget extends View<AlertPresenter> {
		public void show(String caption, String text);
	}

	private HasWidgets container;
	private AlertWidget widget = InjectorHolder.getInstance().getAlertWidget();

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		widget.setPresenter(this);
	}

	public void show(String caption, String text) {
		widget.show(caption, text);
		container.add(widget.asWidget());
	}

	public void onHideButtonClicked() {
		container.remove(widget.asWidget());
	}

}
