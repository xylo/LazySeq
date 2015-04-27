package com.blogspot.nurkiewicz.lazyseq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Tomasz Nurkiewicz
 * @since 5/8/13, 9:08 PM
 */
class Cons<E> extends LazySeq<E> {
	private final E head;
	private volatile LazySeq<E> tailOrNull;
	private final Supplier<LazySeq<E>> tailFun;

	Cons(@NotNull E head, @NotNull Supplier<LazySeq<E>> tailFun) {
		this.head = Objects.requireNonNull(head);
		this.tailFun = Objects.requireNonNull(tailFun);
	}

	@NotNull
	@Override
	public E head() {
		return head;
	}

	@NotNull
	@Override
	public LazySeq<E> tail() {
		if (!isTailDefined()) {
			synchronized (this) {
				if (!isTailDefined()) {
					tailOrNull = tailFun.get();
				}
			}
		}
		return tailOrNull;
	}

	@Override
	protected boolean isTailDefined() {
		return tailOrNull != null;
	}

	@NotNull
	public <R> LazySeq<R> map(@NotNull Function<? super E, ? extends R> mapper) {
		return cons(mapper.apply(head()), () -> tail().map(mapper));
	}

	@NotNull
	@Override
	public <R> LazySeq<R> flatMap(@NotNull Function<? super E, ? extends Iterable<? extends R>> mapper) {
		final ArrayList<R> result = new ArrayList<>();
		mapper.apply(head).forEach(result::add);
		return concat(result, () -> tail().flatMap(mapper));
	}

	@NotNull
	@Override
	protected LazySeq<E> takeUnsafe(long maxSize) {
		if (maxSize > 1) {
			return cons(head, () -> tail().takeUnsafe(maxSize - 1));
		} else {
			return LazySeq.of(head);
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

}