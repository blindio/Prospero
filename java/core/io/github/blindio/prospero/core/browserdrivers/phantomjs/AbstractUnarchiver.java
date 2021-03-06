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
package io.github.blindio.prospero.core.browserdrivers.phantomjs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.lang3.SystemUtils;

public abstract class AbstractUnarchiver implements UnArchiver {

    final static int BUFFER = 2048;

    private File srcFile;
    private File destDir;

    public void setSourceFile(File sourceFile) {
	srcFile = sourceFile;
    }

    public File getSourceFile() {
	return srcFile;
    }

    public void setDestDirectory(File destDirectory) {
	destDir = destDirectory;
    }

    public File getDestDirectory() {
	return destDir;
    }

    abstract public void extract();

    protected void extract(ArchiveInputStream arcInStream) throws IOException {
	ArchiveEntry entry = null;

	/** Read the tar entries using the getNextEntry method **/

	while ((entry = (ArchiveEntry) arcInStream.getNextEntry()) != null) {

	    System.out.println("Extracting: " + entry.getName());

	    /** If the entry is a directory, create the directory. **/

	    if (entry.isDirectory()) {

		File f = new File(getDestDirectory()
			+ SystemUtils.FILE_SEPARATOR + entry.getName());
		f.mkdirs();
	    }
	    /**
	     * If the entry is a file,write the decompressed file to the disk
	     * and close destination stream.
	     **/
	    else {
		int count;
		byte data[] = new byte[BUFFER];

		FileOutputStream fos = new FileOutputStream(getDestDirectory()
			+ SystemUtils.FILE_SEPARATOR + entry.getName());
		BufferedOutputStream dest = new BufferedOutputStream(fos,
			BUFFER);
		while ((count = arcInStream.read(data, 0, BUFFER)) != -1) {
		    dest.write(data, 0, count);
		}
		dest.close();
	    }
	}

	/** Close the input stream **/

	arcInStream.close();
	System.out.println("untar completed successfully!!");
    }
}
