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

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import de.voot.dropboxgwt.client.DropboxWrapper;
import de.voot.dropboxgwt.client.ProgressCallback;
import de.voot.encfsanywhere.client.HistoryItems;
import de.voot.encfsanywhere.client.event.AlertEvent;
import de.voot.encfsanywhere.client.event.DropboxConnectedEvent;
import de.voot.encfsanywhere.client.event.DropboxConnectedEventHandler;
import de.voot.encfsanywhere.client.gin.Injector;
import de.voot.encfsanywhere.client.gin.InjectorHolder;
import de.voot.encfsanywhere.client.model.DownloadStatus;
import de.voot.encfsanywhere.client.presenter.ListPresenter;
import de.voot.encfsanywhere.client.util.Async;
import de.voot.encfsanywhere.client.util.BrowserUtil;
import de.voot.encfsanywhere.client.view.util.UIUtil;
import de.voot.encfsanywhere.fs.shared.Files;
import de.voot.encfsanywhere.fs.shared.Path;
import de.voot.encfsanywhere.fs.shared.providers.DropboxFileProvider;
import de.voot.encfsanywhere.fs.shared.providers.FileProvider;
import de.voot.encfsgwt.shared.jre.InputStream;

public class ListController implements Controller {

	private static final Logger LOG = Logger.getLogger("de.voot.encfsanywhere.client.controller.ListController");

	private Injector injector = InjectorHolder.getInstance();
	private HandlerManager eventBus = injector.getHandlerManager();
	private ListPresenter listPresenter = injector.getListPresenter();
	private HasWidgets container;

	private Files files;
	private Path currentPath;
	private List<DownloadStatus> downloadsInProgress = new LinkedList<DownloadStatus>();

	@Override
	public void init(HasWidgets container) {
		this.container = container;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(DropboxConnectedEvent.TYPE, new DropboxConnectedEventHandler() {
			@Override
			public void onDropboxConnected(DropboxWrapper dropboxWrapper) {
				bindDropboxProgessListener(dropboxWrapper);
				fileProviderConnected(new DropboxFileProvider(dropboxWrapper));
			}
		});
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			if (token.startsWith(HistoryItems.LIST_PREFIX)) {
				if (files == null) {
					History.newItem(HistoryItems.STORAGE_AUTO_CONNECT);
				} else {
					String path = token.substring(HistoryItems.LIST_PREFIX.length());
					showFilesEvent(path);
				}
			}
		}
	}

	private void fileProviderConnected(FileProvider encFSFileProvider) {
		LOG.info("Received file provider");
		files = new Files(encFSFileProvider);
	}

	private void bindDropboxProgessListener(DropboxWrapper dropboxWrapper) {
		dropboxWrapper.addProgressListener(new ProgressCallback() {

			@Override
			public void onProgress(String path, float progress) {
				LOG.info("Download progress: " + progress + " downloaded of <" + path + ">.");
				updateDownloadListWithProgress(path, progress);
			}
		});
	}

	private void showFilesEvent(String path) {
		LOG.info("Preparing to change to path <" + path + ">");
		String validatedPath = validatePath(path);

		if (!path.equals(validatedPath)) {
			History.newItem(HistoryItems.LIST_PREFIX + validatedPath, false);
		}

		files.pathForName(validatedPath, Async.wrap(new Callback<Path, Exception>() {
			@Override
			public void onSuccess(Path path) {
				currentPath = path;
				showFiles(path);
			}

			@Override
			public void onFailure(Exception reason) {
				LOG.log(Level.SEVERE, "Couldn't get path object for path string", reason);
			}
		}));
	}

	private void showFiles(final Path path) {
		showFiles(path, false);
	}

	private void showFiles(final Path path, final boolean disablePasswordPrompt) {
		LOG.info("Loading list of files for path <" + path + "> and presenting");

		files.listFiles(path, Async.wrap(new Callback<Path[], Exception>() {
			@Override
			public void onFailure(Exception reason) {
				LOG.log(Level.SEVERE, "Exception getting list of files for path: " + path, reason);
			}

			@Override
			public void onSuccess(final Path[] result) {
				LOG.info("Received list of files");

				listPresenter.go(container);
				if (!disablePasswordPrompt && files.isEncFSRoot(path) && !files.isEncFSRootUnlocked(path)) {
					listPresenter.promptForPassword();
				} else {
					listPresenter.listFiles(files, path, result);
				}
			}
		}));
	}

	public void passwordPromptFinished(String password) {
		if (password != null) {
			unlock(password);
		} else {
			LOG.info("No password provided");
			showFiles(currentPath, true);
		}
	}

	private void unlock(String password) {
		LOG.info("Unlocking encfs volume");
		UIUtil.showKeyDerivationOverlay();
		files.unlock(currentPath, password, new Callback<Void, Exception>() {
			@Override
			public void onFailure(Exception reason) {
				UIUtil.hideKeyDerivationOverlay();
				LOG.log(Level.SEVERE, "Couldn't unlock encfs volume", reason);
			}

			@Override
			public void onSuccess(Void v) {
				LOG.info("Unlock of volume successful");
				UIUtil.hideKeyDerivationOverlay();
				showFiles(currentPath);
			}
		});
	}

	private String validatePath(String path) {
		return path.isEmpty() ? "/" : path;
	}

	public void showPath(String path) {
		History.newItem(HistoryItems.LIST_PREFIX + path);
	}

	public void downloadPath(final Path path) {
		LOG.info("Initiating download of path: " + path);

		if (downloadListIndex(path.toString()) > -1) {
			LOG.info("File already downloading. Not starting new download.");
			eventBus.fireEvent(new AlertEvent("Already downloading", "File is already downloading. Didn't start a second download."));
			return;
		}

		addToDownloadList(path.toString());

		files.getFileContent(path, new Callback<InputStream, Exception>() {
			@Override
			public void onFailure(Exception reason) {
				removeFromDownloadList(path.toString());
				LOG.log(Level.SEVERE, "Couldn't receive file contents.", reason);
			}

			@Override
			public void onSuccess(InputStream result) {
				removeFromDownloadList(path.toString());
				BrowserUtil.download(result, files.getName(path));
			}
		});
	}

	private int downloadListIndex(String path) {
		for (int i = 0; i < downloadsInProgress.size(); i++) {
			if (downloadsInProgress.get(i).getPath().equals(path)) {
				return i;
			}
		}
		return -1;
	}

	private void addToDownloadList(String path) {
		downloadsInProgress.add(new DownloadStatus(path.toString()));
		listPresenter.showDownloads(downloadsInProgress);
	}

	private void removeFromDownloadList(String path) {
		int index = downloadListIndex(path);
		if (index > -1) {
			downloadsInProgress.remove(index);
			listPresenter.showDownloads(downloadsInProgress);
		}
	}

	private void updateDownloadListWithProgress(String path, float progress) {
		int index = downloadListIndex(path);
		if (index == -1) {
			// no entry found with name path. Maybe its within an encfs volume
			// and needs decryption.
			path = files.decryptCachedPathname(path);
			index = downloadListIndex(path);
		}
		if (index > -1) {
			downloadsInProgress.get(index).setProgress(progress);
			listPresenter.showDownloads(downloadsInProgress);
		}

	}

	public void disconnect() {
		LOG.log(Level.INFO, "Disconnecting from storage provider");
		files.disconnect();
		files = null;
		History.newItem(HistoryItems.STORAGE_CONNECT);
	}
}
