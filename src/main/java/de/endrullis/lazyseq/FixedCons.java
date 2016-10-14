package de.endrullis.lazyseq;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Tomasz Nurkiewicz
 * @since 5/8/13, 9:09 PM
 */
class FixedCons<E> extends LazySeq<E> {

	private final E head;
	private final LazySeq<E> tail;

	public FixedCons(E head, LazySeq<E> tail) {
		this.head = Objects.requireNonNull(head);
		this.tail = Objects.requireNonNull(tail);
	}

	@NotNull
	@Override
	public E head() {
		return head;
	}

	@NotNull
	@Override
	public LazySeq<E> tail() {
		return tail;
	}

	@Override
	protected boolean isTailDefined() {
		return true;
	}

	@NotNull
	@Override
	public <R> LazySeq<R> map(@NotNull Function<? super E, ? extends R> mapper) {
		return cons(mapper.apply(head), tail.map(mapper));
	}

	@NotNull
	@Override
	public <R> LazySeq<R> flatMap(@NotNull Function<? super E, ? extends Iterable<? extends R>> mapper) {
		final ArrayList<R> result = new ArrayList<>();
		mapper.apply(head).forEach(result::add);
		return concat(result, tail.flatMap(mapper));
	}

	@NotNull
	@Override
	protected LazySeq<E> takeUnsafe(long maxSize) {
		if (maxSize > 1) {
			return cons(head, tail.takeUnsafe(maxSize - 1));
		} else {
			return LazySeq.of(head);
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

}