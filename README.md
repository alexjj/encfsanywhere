# EncfsAnywhere
An EncFS capable fileviewer for Dropbox that runs entirely in your browser.

If you are using EncFS to keep your files safe and want to be able to access them from any computer that provides a browser, this app might interest you. EncfsAnyhwere is able to connect to your Dropbox and decrypt EncFS folders you store in there. Your EncFS password never leaves your computer, nor are any decrypted files transferred anywhere! The encrypted files are loaded from your Dropbox and decrypted within your browser. Everything happens on your computer, not on any server!

It is written in Java and compiled to a HTML/JavaScript app using GWT.

## Getting started
Your encrypted EncFS folders need to be accessible by EncfsAnyhwere: Upon the first connect of EncfsAnywhere with your Dropbox, an application folder will be created at `~/Dropbox/Apps/EncFSAnyhwere/`. Thats where you either place your encrypted folders, or from where you symlink to them.

Then you need a copy of EncfsAnyhwere:

* You find a copy at <https://marcoschulte.bitbucket.org/EncfsAnywhere/EncfsAnywhere.html>
* Download your copy from the download section and upload it somewhere, so it's accessible for you from everywhere you are
* Clone the project and compile it yourself

## Compatibility
### Browsers
EncfsAnywhere has been tested successfully with the following browsers

* Chrome >= 27
* Firefox >= 22

### EncFS
The EncFS volume you want to view needs to use AES as cipher, Blowfish is not supported.

All other settings should work, but aren't extensivly tested. But I can assure that EncFS' paranoia mode is working.

## Dependencies
* EncfsAnywhere-fs (<https://bitbucket.org/marcoschulte/encfsanywhere-fs>)

## License
EncfsAnywhere is licensed under the GNU General Public License (GPL) 3.0.