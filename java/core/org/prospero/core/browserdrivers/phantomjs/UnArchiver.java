package org.prospero.core.browserdrivers.phantomjs;

import java.io.File;


public interface UnArchiver {
	
	public void setSourceFile(File sourceFile);
	
	public File getSourceFile();
	
	public void setDestDirectory(File destDirectory);
	
	public File getDestDirectory();
	
	public void extract();
	
}
