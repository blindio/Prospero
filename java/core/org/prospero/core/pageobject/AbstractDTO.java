package org.prospero.core.pageobject;

import net.sf.json.JSONSerializer;

public abstract class AbstractDTO {
	
	public static final int JSON_TOSTRING_INDENT = 1;
	
	@Override
	public String toString() {
		return JSONSerializer.toJSON(this).toString(JSON_TOSTRING_INDENT);
	}
	
}
