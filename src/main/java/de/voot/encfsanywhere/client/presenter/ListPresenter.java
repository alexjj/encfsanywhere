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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasWidgets;

import de.voot.encfsanywhere.client.gin.Injector;
import de.voot.encfsanywhere.client.gin.InjectorHolder;
import de.voot.encfsanywhere.client.model.DownloadStatus;
import de.voot.encfsanywhere.client.view.View;
import de.voot.encfsanywhere.fs.shared.Files;
import de.voot.encfsanywhere.fs.shared.Path;

public class ListPresenter implements Presenter {

	public interface ListView extends View<ListPresenter> {
		public void showList(String path, String[] content, Boolean[] isDirectory);

		public void showDownloads(String[] paths, String[] progress);

		public void promptForPassword();
	}

	private Injector injector = InjectorHolder.getInstance();
	private ListView view = injector.getListView();

	private Files files;
	private Path[] shownFiles;

	public ListPresenter() {
		view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	public void listFiles(Files files, Path currentPath, Path[] content) {
		this.files = files;

		content = sort(content);
		this.shownFiles = content;

		String[] filenames = new String[content.length];
		Boolean[] isDirectory = new Boolean[content.length];
		for (int i = 0; i < content.length; i++) {
			filenames[i] = files.getName(content[i]);
			isDirectory[i] = files.isDirectory(content[i]);
		}

		view.showList(currentPath.toString(), filenames, isDirectory);
	}

	public void showDownloads(List<DownloadStatus> status) {
		String paths[] = new String[status.size()];
		String progress[] = new String[status.size()];

		for (int i = 0; i < paths.length; i++) {
			DownloadStatus s = status.get(i);
			paths[i] = s.getPath();
			NumberFormat format = NumberFormat.getPercentFormat();
			progress[i] = format.format(s.getProgress());
		}

		view.showDownloads(paths, progress);
	}

	private Path[] sort(Path[] path) {
		List<Path> list = new ArrayList<Path>();
		for (Path p : path) {
			list.add(p);
		}

		Collections.sort(list, new Comparator<Path>() {
			@Override
			public int compare(Path o1, Path o2) {
				String name1 = o1.toString().substring(o1.toString().lastIndexOf("/") + 1);
				String name2 = o2.toString().substring(o2.toString().lastIndexOf("/") + 1);
				return name1.compareTo(name2);
			}
		});

		return list.toArray(new Path[0]);
	}

	public void onRowClicked(int row) {
		if (files.isDirectory(shownFiles[row])) {
			injector.getListController().showPath(shownFiles[row].toString());
		} else {
			injector.getListController().downloadPath(shownFiles[row]);
		}
	}

	public void promptForPassword() {
		view.promptForPassword();
	}

	public void onPasswordPromptFinished(String password) {
		injector.getListController().passwordPromptFinished(password);
	}

	public void onDisconnectClicked() {
		injector.getListController().disconnect();
	}
}
