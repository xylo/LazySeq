package com.blogspot.nurkiewicz.lazyseq;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.*;

class Nil<E> extends LazySeq<E> {

	private static final Nil<?> NIL = new Nil<>();

	@SuppressWarnings("unchecked")
	public static <E> Nil<E> instance() {
		return (Nil<E>) NIL;
	}

	@NotNull
	@Override
	public E head() {
		throw new NoSuchElementException("head of empty stream");
	}

	@NotNull
	@Override
	public Option<E> headOption() {
		return Option.empty();
	}

	@NotNull
	@Override
	public LazySeq<E> tail() {
		throw new NoSuchElementException("tail of empty stream");
	}

	@Override
	protected boolean isTailDefined() {
		return false;
	}

	@NotNull
	@Override
	public E get(int index) {
		throw new IndexOutOfBoundsException(Integer.toString(index));
	}

	@NotNull
	@Override
	public <R> LazySeq<R> map(@NotNull Function<? super E, ? extends R> mapper) {
		return instance();
	}

	@NotNull
	@Override
	public LazySeq<E> filter(@NotNull Predicate<? super E> predicate) {
		return instance();
	}

	@NotNull
	@Override
	public <R> LazySeq<R> flatMap(@NotNull Function<? super E, ? extends Iterable<? extends R>> mapper) {
		return instance();
	}

	@NotNull
	@Override
	protected LazySeq<E> takeUnsafe(long maxSize) {
		return instance();
	}

	@Override
	protected LazySeq<E> dropUnsafe(long startInclusive) {
		return instance();
	}

	@Override
	public void forEach(@NotNull Consumer<? super E> action) {
		//no op
	}

	@NotNull
	@Override
	public Option<E> min(@NotNull Comparator<? super E> comparator) {
		return Option.empty();
	}

	@NotNull
	@Override
	public Option<E> max(@NotNull Comparator<? super E> comparator) {
		return Option.empty();
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean anyMatch(@NotNull Predicate<? super E> predicate) {
		return false;
	}

	@Override
	public boolean allMatch(@NotNull Predicate<? super E> predicate) {
		return true;
	}

	@NotNull
	@Override
	public <S, R> LazySeq<R> zip(@NotNull LazySeq<? extends S> second, @NotNull BiFunction<? super E, ? super S, ? extends R> zipper) {
		return instance();
	}

	@NotNull
	@Override
	public LazySeq<E> takeWhile(@NotNull Predicate<? super E> predicate) {
		return instance();
	}

	@NotNull
	@Override
	public LazySeq<E> dropWhile(@NotNull Predicate<? super E> predicate) {
		return instance();
	}

	@NotNull
	@Override
	public LazySeq<List<E>> slidingUnsafe(int size) {
		return instance();
	}

	@NotNull
	@Override
	protected LazySeq<List<E>> groupedUnsafe(int size) {
		return instance();
	}

	@NotNull
	@Override
	public LazySeq<E> scan(@NotNull E initial, @NotNull BinaryOperator<E> fun) {
		return of(initial);
	}

	@NotNull
	@Override
	public LazySeq<E> distinct() {
		return instance();
	}

	@Override
	public boolean startsWith(Iterator<E> iterator) {
		return !iterator.hasNext();
	}

	@NotNull
	@Override
	public LazySeq<E> force() {
		return this;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Nil;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}
}