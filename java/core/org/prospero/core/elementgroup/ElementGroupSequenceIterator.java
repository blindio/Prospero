package org.prospero.core.elementgroup;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * ListIterator (i.e. reversible iterator) for ElementGroups in this ElementGroupSequence
 * 
 * @author tlittle
 * @param <E>
 */
public class ElementGroupSequenceIterator<E extends ElementGroup> implements ListIterator<E> {
	
	private ElementGroupSequence<E> sequence;
	private int nextIndex;
	
	
	public ElementGroupSequenceIterator(ElementGroupSequence<E> sequence) {
		this.sequence = sequence;
		this.nextIndex = 0;
	}
	
	/**
	 * Interface ListIterator allows for add() operation to be optional. It is not supported in this
	 * implemention. Throws a UnsupportedOperationException.
	 */
	@Override
	public void add(E arg0) {
		throw new UnsupportedOperationException(
				"Interface ListIterator allows for add() operation to be optional.  It is not supported in this implemention.");
	}
	
	@Override
	public boolean hasNext() {
		return sequence.getElementGroup(nextIndex()).isPresent();
	}
	
	@Override
	public boolean hasPrevious() {
		return sequence.getElementGroup(previousIndex()).isPresent();
	}
	
	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException("Next element does not exist");
		}
		return sequence.getElementGroup(nextIndex++);
	}
	
	@Override
	public int nextIndex() {
		// From ListIterator javadocs:
		// "the index of the element that would be returned by a subsequent call to next,
		// or list size if list iterator is at end of list."
		// this implies that it will always return current index + 1 since the length == lastIndex + 1
		return nextIndex;
	}
	
	@Override
	public E previous() {
		if (!hasNext()) {
			throw new NoSuchElementException("Previous element does not exist");
		}
		return sequence.getElementGroup(--nextIndex);
	}
	
	@Override
	public int previousIndex() {
		return nextIndex - 1;
	}
	
	/**
	 * Interface ListIterator allows for remove() operation to be optional. It is not supported in this
	 * implemention. Throws a UnsupportedOperationException.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException(
				"Interface ListIterator allows for remove() operation to be optional.  It is not supported in this implemention.");
		
	}
	
	/**
	 * Interface ListIterator allows for set() operation to be optional. It is not supported in this
	 * implemention. Throws a UnsupportedOperationException.
	 */
	@Override
	public void set(E arg0) {
		throw new UnsupportedOperationException(
				"Interface ListIterator allows for set() operation to be optional.  It is not supported in this implemention.");
	}
	
}
