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

import io.github.blindio.prospero.core.exceptions.ProsperoIOException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;


public class ZipUnArchiver extends AbstractUnarchiver {

    public void extract() {

	/** create a TarArchiveInputStream object. **/
	try {
	    FileInputStream fin = new FileInputStream(getSourceFile());
	    BufferedInputStream in = new BufferedInputStream(fin);
	    ArchiveInputStream arcIn = new ZipArchiveInputStream(in);

	    extract(arcIn);
	} catch (IOException ioe) {
	    throw new ProsperoIOException(ioe);
	}
    }

}
