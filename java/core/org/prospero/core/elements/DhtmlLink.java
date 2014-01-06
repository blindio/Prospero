package org.prospero.core.elements;

/**
 * Element representing a Dynamic HTML link opening a new container, rather than causing a page load
 * 
 * @author tlittle
 * @param <T>
 */
public class DhtmlLink<T> extends ClickableElement {
	
	private Class<T> clazz;
	
	public DhtmlLink(String locator, Class<T> clazz) {
		super(locator);
		this.clazz = clazz;
	}
	
	public T clickToNewContainer() {
		try {
			click();
			return clazz.getConstructor(new Class[] {}).newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
