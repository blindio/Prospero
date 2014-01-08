/*******************************************************************************
 * Copyright 2014 S. Thorson Little
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.prospero.core.browserdrivers.phantomjs;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import org.prospero.core.exceptions.ProsperoRuntimeAutomationException;

public class PhantomJSInstaller {

    public static final String PHANTOMJS_FILE_PREFIX = "phantomjs";
    public static final String PHANTOMJS_VERSION = "1.9.1";
    public static final String PHANTOMJS_INSTALL_DIR = "phantomjs";

    public static String getPhantomJS() {
	File phantomJS = getPhantomJSExecutableFile();
	if (!phantomJS.exists()) {
	    installPhantomJS();
	}

	if (!phantomJS.canExecute()) {
	    phantomJS.setExecutable(true, false);
	}

	return phantomJS.getAbsolutePath();
    }

    static void installPhantomJS() {
	File tempArchive = null;
	try {
	    tempArchive = File.createTempFile(PHANTOMJS_FILE_PREFIX,
		    ArchiveFormat.getFileExtention());
	    FileUtils
		    .copyURLToFile(
			    new URL(PhantomJSArchiveFile.getDownloadURL()),
			    tempArchive);
	    tempArchive.deleteOnExit();
	    UnArchiver unarchiver = ArchiveFormat.getUnArchiver();
	    unarchiver.setSourceFile(tempArchive);
	    File destDir = new File(getPhantomJSInstallDirPath());
	    if (!destDir.exists()) {
		destDir.mkdir();
	    }
	    unarchiver.setDestDirectory(destDir);
	    unarchiver.extract();
	} catch (Exception e) {
	    throw new ProsperoRuntimeAutomationException(e);
	}
    }

    public static File getPhantomJSExecutableFile() {
	return new File(getPhantomJSExecutablePath());
    }

    public static String getPhantomJSExecutablePath() {
	return getPhantomJSInstallDirPath()
		+ PhantomJSArchiveFile.getExecutablePath();
    }

    public static String getPhantomJSInstallDirPath() {
	return SystemUtils.getUserDir().getAbsolutePath()
		+ SystemUtils.FILE_SEPARATOR + PHANTOMJS_INSTALL_DIR
		+ SystemUtils.FILE_SEPARATOR;
    }

    private enum PhantomJSArchiveFile {
	WINDOWS(PhantomJSArchiveFile.WINDOWS_OS,
		PhantomJSArchiveFile.WINDOWS_EXEC_PATH), MAC(
		PhantomJSArchiveFile.MAC_OS,
		PhantomJSArchiveFile.UNIX_EXEC_PATH), LINUX_32(
		PhantomJSArchiveFile.LINUX_32_OS,
		PhantomJSArchiveFile.UNIX_EXEC_PATH), LINUX_64(
		PhantomJSArchiveFile.LINUX_64_OS,
		PhantomJSArchiveFile.UNIX_EXEC_PATH);

	private String osStr;
	private String execPath;
	private static PhantomJSArchiveFile phantomJSFile = null;

	static final String DOWNLOAD_LOCATION = "https://phantomjs.googlecode.com/files/";
	static final String FILENAME_SEPARATOR = "-";
	static final String WINDOWS_OS = "windows";
	static final String MAC_OS = "macosx";
	static final String LINUX_32_OS = "linux-i686";
	static final String LINUX_64_OS = "linux-x86_64";
	static final String WINDOWS_EXEC_PATH = "phantomjs.exe";
	static final String UNIX_EXEC_PATH = "bin/phantomjs";

	private PhantomJSArchiveFile(String os, String executablePath) {
	    osStr = os;
	    execPath = executablePath;
	}

	private static String getArchiveFileName() {
	    return getArchiveName() + ArchiveFormat.getFileExtention();
	}

	private static String getArchiveName() {
	    return PhantomJSInstaller.PHANTOMJS_FILE_PREFIX
		    + FILENAME_SEPARATOR + PHANTOMJS_VERSION
		    + FILENAME_SEPARATOR + getPhantomJSFile().osStr;
	}

	private static String getDownloadURL() {
	    return DOWNLOAD_LOCATION + getArchiveFileName();
	}

	private static String getExecutablePath() {
	    return getArchiveName() + SystemUtils.FILE_SEPARATOR
		    + getPhantomJSFile().execPath;
	}

	private static PhantomJSArchiveFile getPhantomJSFile() {
	    if (phantomJSFile == null) {
		if (SystemUtils.IS_OS_WINDOWS) {
		    phantomJSFile = PhantomJSArchiveFile.WINDOWS;
		} else if (SystemUtils.IS_OS_MAC_OSX) {
		    phantomJSFile = PhantomJSArchiveFile.MAC;
		} else if (SystemUtils.IS_OS_LINUX) {
		    if (SystemUtils.OS_ARCH.endsWith("64")) {
			phantomJSFile = PhantomJSArchiveFile.LINUX_64;
		    } else {
			phantomJSFile = PhantomJSArchiveFile.LINUX_32;
		    }
		} else {
		    throw new ProsperoRuntimeAutomationException(
			    "Unsupported Operating System");
		}
	    }
	    return phantomJSFile;
	}
    }

    private enum ArchiveFormat {
	BZIP2(new TarBZip2UnArchiver(), ArchiveFormat.ARCHIVE_FILE_EXT_TARBZ2), ZIP(
		new ZipUnArchiver(), ArchiveFormat.ARCHIVE_FILE_EXT_ZIP);

	static final String ARCHIVE_FILE_EXT_ZIP = ".zip";
	static final String ARCHIVE_FILE_EXT_TARBZ2 = ".tar.bz2";

	private UnArchiver unarch;
	private String fileExt;
	private static ArchiveFormat arcFormat = null;

	private ArchiveFormat(UnArchiver unarchiver, String fileExtension) {
	    unarch = unarchiver;
	    fileExt = fileExtension;
	}

	private static UnArchiver getUnArchiver() {
	    return getArchiveFormat().unarch;
	}

	private static String getFileExtention() {
	    return getArchiveFormat().fileExt;
	}

	private static ArchiveFormat getArchiveFormat() {
	    if (arcFormat == null) {
		if (SystemUtils.IS_OS_WINDOWS) {
		    arcFormat = ArchiveFormat.ZIP;
		} else if (SystemUtils.IS_OS_MAC) {
		    arcFormat = ArchiveFormat.ZIP;
		} else if (SystemUtils.IS_OS_UNIX) {
		    arcFormat = ArchiveFormat.BZIP2;
		} else {
		    throw new ProsperoRuntimeAutomationException(
			    "Unsupported Operating System");
		}
	    }
	    return arcFormat;
	}
    }

}
