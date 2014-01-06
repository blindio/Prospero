package org.prospero.core.elementgroup;

import org.prospero.core.exceptions.ProsperoRuntimeAutomationException;


/**
 * Represents a ordered collection of ElementGroups, such as rows in a table. <br/>
 * This is implemented by making every ElementGroup of a sequence contain a common prefix appended with an
 * index number.
 * 
 * @author tlittle
 * @param <T>
 */
public class ElementGroupSequence<T extends ElementGroup> implements Iterable<T> {
	
	private String elementGroupIdPrefix;
	private Class<T> clazz;
	private int length = 0;
	
	public ElementGroupSequence(String elementGroupIdPrefix, Class<T> clazz, int firstIndex) {
		this.elementGroupIdPrefix = elementGroupIdPrefix;
		this.clazz = clazz;
	}
	
	public ElementGroupSequence(String locatorPrefix, Class<T> clazz) {
		this(locatorPrefix, clazz, 0);
	}
	
	/**
	 * Returns an ElementGroup in this ElementGroupSequence reference by the index number.
	 * 
	 * @param index
	 * @return
	 */
	public T getElementGroup(int index) {
		String elementGroupId = elementGroupIdPrefix + "." + index;
		try {
			return clazz.getConstructor(new Class[] { String.class }).newInstance(
					new Object[] { elementGroupId });
		}
		catch (Exception e) {
			throw new ProsperoRuntimeAutomationException(e);
		}
	}
	
	@Override
	public ElementGroupSequenceIterator<T> iterator() {
		return new ElementGroupSequenceIterator<T>(this);
	}
	
	public int getLength() {
		if (length == 0) {
			ElementGroupSequenceIterator<T> iter = iterator();
			while (iter.hasNext()) {
				iter.next();
			}
			length = iter.nextIndex();
		}
		
		return length;
	}
}
