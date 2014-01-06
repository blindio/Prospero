package org.prospero.core.browserdrivers.phantomjs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import org.prospero.core.exceptions.ProsperoIOException;

public class ZipUnArchiver extends AbstractUnarchiver {
	
	public void extract() {
		
		/** create a TarArchiveInputStream object. **/
		try {
			FileInputStream fin = new FileInputStream(getSourceFile());
			BufferedInputStream in = new BufferedInputStream(fin);
			ArchiveInputStream arcIn = new ZipArchiveInputStream(in);
			
			extract(arcIn);
		}
		catch (IOException ioe) {
			throw new ProsperoIOException(ioe);
		}
	}
	
}
