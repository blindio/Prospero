package org.prospero.core.elementgroup;

public interface ElementGroup {
	
	/**
	 * Returns the id prefix for this ElementGroup.<br/>
	 * This id is prepended to the suffix of an element in the group to build the full index. <br/>
	 * For example if the ElementGroup Id is 'prospero.joblist.0' and the element's id suffix is 'detaillink',
	 * the full id for this element is 'prospero.joblist.0.detaillink'.
	 * 
	 * @return
	 */
	public String getId();
	
	/**
	 * Returns the id for an element in this element group. Does this by prepending the ElementGroup's id to
	 * the elementIdSuffix. <br/>
	 * For example if the ElementGroup Id is 'prospero.joblist.0' and the element's id suffix is 'detaillink',
	 * the full id for this element is 'prospero.joblist.0.detaillink'.
	 * 
	 * @param elementIdSuffix
	 * @return
	 */
	public String buildElementId(String elementIdSuffix);
	
	/**
	 * Test to determine whether this ElementGroup is present
	 * 
	 * @return
	 */
	public boolean isPresent();
	
}
