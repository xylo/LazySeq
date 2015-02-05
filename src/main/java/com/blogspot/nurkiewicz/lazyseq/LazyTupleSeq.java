package com.blogspot.nurkiewicz.lazyseq;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collectors;

import static com.blogspot.nurkiewicz.lazyseq.Shortcuts.tupled;

/**
 * Quick hack for having a {@link LazySeq} with tuples that provides also some map related functions such as
 * <ul>
 *   <li>{@link #mapKeys(java.util.function.Function)}</li>
 *   <li>{@link #mapValues(java.util.function.Function)}</li>
 *   <li>{@link #toMap()}</li>
 * </ul>
 *
 * @author Stefan Endrullis (stefan@endrullis.de)
 */
public class LazyTupleSeq<K,V> extends LazySeq<Tuple<K,V>> {
	protected final Supplier<Map<K, V>>            underlyingMap;
	protected final Supplier<LazySeq<Tuple<K, V>>> underlyingSeq;
	protected final IntSupplier                    underlyingSize;
	protected final boolean                        empty;

	public LazyTupleSeq(Map<K, V> map) {
		this.underlyingMap = () -> map;
		this.underlyingSeq = () -> LazySeq.of(map.entrySet()).map(e -> new Tuple<>(e.getKey(), e.getValue()));
		this.underlyingSize = () -> map.size();
		this.empty = map.isEmpty();
	}

	public LazyTupleSeq(LazySeq<Tuple<K, V>> seq) {
		this.underlyingMap = () -> StreamUtils.toMap(seq.stream(), t -> t._1, t -> t._2);
		this.underlyingSeq = () -> seq;
		this.underlyingSize = () -> seq.size(); this.empty = seq.isEmpty();
	}

	@Override
	public Tuple<K, V> head() {
		return underlyingSeq.get().head();
	}

	@Override
	public Optional<Tuple<K, V>> headOption() {
		if (empty) {
			return Optional.empty();
		} else {
			return super.headOption();
		}
	}

	@Override
	public LazySeq<Tuple<K, V>> tail() {
		return underlyingSeq.get().tail();
	}

	@Override
	protected boolean isTailDefined() {
		return underlyingSeq.get().isTailDefined();
	}

	@Override
	public <R> LazySeq<R> map(Function<? super Tuple<K, V>, ? extends R> mapper) {
		return underlyingSeq.get().map(mapper);
	}

	public <R> LazySeq<R> map(BiFunction<K, V, R> mapper) {
		return map(tupled(mapper));
	}

	@Override
	public <R> LazySeq<R> flatMap(Function<? super Tuple<K, V>, ? extends Iterable<? extends R>> mapper) {
		return underlyingSeq.get().flatMap(mapper);
	}

	public <R> LazySeq<R> flatMap(BiFunction<? super K, ? super V, ? extends Iterable<? extends R>> mapper) {
		return flatMap(tupled(mapper));
	}

	@Override
	public LazyTupleSeq<K, V> filter(Predicate<? super Tuple<K, V>> predicate) {
		return new LazyTupleSeq<>(underlyingSeq.get().filter(predicate));
	}

	public LazyTupleSeq<K, V> filter(BiPredicate<? super K, ? super V> predicate) {
		return filter(tupled(predicate));
	}

	@Override
	public LazyTupleSeq<K,V> takeWhile(Predicate<? super Tuple<K, V>> predicate) {
		return new LazyTupleSeq<>(underlyingSeq.get().takeWhile(predicate));
	}

	public LazyTupleSeq<K,V> takeWhile(BiPredicate<? super K, ? super V> predicate) {
		return takeWhile(tupled(predicate));
	}

	@Override
	public LazyTupleSeq<K,V> dropWhile(Predicate<? super Tuple<K, V>> predicate) {
		return new LazyTupleSeq<>(underlyingSeq.get().dropWhile(predicate));
	}

	public LazyTupleSeq<K,V> dropWhile(BiPredicate<? super K, ? super V> predicate) {
		return dropWhile(tupled(predicate));
	}

	@Override
	public int size() {
		return underlyingSize.getAsInt();
	}

	@Override
	public boolean isEmpty() {
		return empty;
	}

	@Override
	public LazyTupleSeq<K, V> sorted() {
		return sorted((o1, o2) -> ((Comparable<Tuple<K, V>>) o1).compareTo(o2));
	}

	@Override
	public LazyTupleSeq<K, V> sorted(Comparator<? super Tuple<K, V>> comparator) {
		return new LazyTupleSeq<>(super.sorted(comparator));
	}

	@Override
	public LazyTupleSeq<K, V> sortedBy(Function<? super Tuple<K, V>, ? extends Comparable> attribute) {
		return sorted(Comparator.comparing(attribute));
	}

	public LazyTupleSeq<K, V> sortedBy(BiFunction<? super K, ? super V, ? extends Comparable> attribute) {
		return sortedBy(tupled(attribute));
	}

	@Override
	protected LazySeq<Tuple<K, V>> takeUnsafe(long maxSize) {
		return underlyingSeq.get().takeUnsafe(maxSize);
	}

	public <R> LazyTupleSeq<R,V> mapKeys(Function<? super K, ? extends R> keyMapper) {
		return new LazyTupleSeq<R,V>(map(t -> new Tuple<>(keyMapper.apply(t._1), t._2)));
	}

	public <R> LazyTupleSeq<K,R> mapValues(Function<? super V, ? extends R> valueMapper) {
		return new LazyTupleSeq<K,R>(map(t -> new Tuple<>(t._1, valueMapper.apply(t._2))));
	}

	public void forEach(BiConsumer<? super K, ? super V> action) {
		forEach(tupled(action));
	}

	public <C extends Comparable<? super C>> Optional<Tuple<K,V>> maxBy(BiFunction<? super K, ? super V, C> property) {
		return maxBy(tupled(property));
	}

	public <C extends Comparable<? super C>> Optional<Tuple<K,V>> minBy(BiFunction<? super K, ? super V, C> property) {
		return minBy(tupled(property));
	}

	public LazySeq<K> keys() {
		return underlyingSeq.get().map(t -> t._1);
	}

	public LazySeq<V> values() {
		return underlyingSeq.get().map(t -> t._2);
	}

	public Map<K, V> toMap() {
		return underlyingMap.get();
	}

	public boolean anyMatch(BiPredicate<? super K, ? super V> predicate) {
		return anyMatch(tupled(predicate));
	}

	public boolean allMatch(BiPredicate<? super K, ? super V> predicate) {
		return allMatch(tupled(predicate));
	}

	public boolean noneMatch(BiPredicate<? super K, ? super V> predicate) {
		return noneMatch(tupled(predicate));
	}

	public boolean exists(BiPredicate<? super K, ? super V> predicate) {
		return exists(tupled(predicate));
	}

	public boolean forall(BiPredicate<? super K, ? super V> predicate) {
		return forall(tupled(predicate));
	}

	@Override
	public <S, R> LazySeq<R> zip(LazySeq<? extends S> second, BiFunction<? super Tuple<K, V>, ? super S, ? extends R> zipper) {
		if (this.isEmpty()) {
			return empty();
		} else {
			return super.zip(second, zipper);
		}
	}

}
