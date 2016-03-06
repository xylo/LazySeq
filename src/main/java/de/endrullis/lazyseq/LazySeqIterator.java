package de.endrullis.lazyseq;

import de.endrullis.lazyseq.LazySeq;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author Tomasz Nurkiewicz
 * @since 5/9/13, 2:15 PM
 */
public class LazySeqIterator<E> implements Iterator<E> {

	private boolean getUnderlyingTail = false;
	private LazySeq<E> underlying;

	public LazySeqIterator(LazySeq<E> lazySeq) {
		this.underlying = lazySeq;
	}

	@Override
	public boolean hasNext() {
		return !underlying().isEmpty();
	}

	@Override
	public E next() {
		final E next = underlying().head();
		getUnderlyingTail = true;
		return next;
	}

	private LazySeq<E> underlying() {
		if (getUnderlyingTail) {
			underlying = underlying.tail();
			getUnderlyingTail = false;
		}
		return underlying;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public void forEachRemaining(Consumer<? super E> action) {
		underlying().forEach(action);
	}
}
