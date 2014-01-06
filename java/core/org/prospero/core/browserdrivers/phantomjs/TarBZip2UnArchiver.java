package org.prospero.core.browserdrivers.phantomjs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import org.prospero.core.exceptions.ProsperoIOException;


public class TarBZip2UnArchiver extends AbstractUnarchiver {
	
	public void extract() {
		/** create a TarArchiveInputStream object. **/
		
		try {
			FileInputStream fin = new FileInputStream(getSourceFile());
			BufferedInputStream in = new BufferedInputStream(fin);
			BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
			ArchiveInputStream arcIn = new TarArchiveInputStream(bzIn);
			
			extract(arcIn);
		}
		catch (IOException ioe) {
			throw new ProsperoIOException(ioe);
		}
	}
	
}
