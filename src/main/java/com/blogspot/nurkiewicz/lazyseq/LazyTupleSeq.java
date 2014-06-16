package com.blogspot.nurkiewicz.lazyseq;

import java.util.Comparator;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Quick hack for having a {@link LazySeq} with tuples that provides also some map related functions such as
 * <ul>
 *   <li>{@link #mapKeys(java.util.function.Function)}</li>
 *   <li>{@link #mapValues(java.util.function.Function)}</li>
 *   <li>{@link #toMap()}</li>
 * </ul>
 *
 * @author Stefan Endrullis (stefan@endrullis.de)
 * @deprecated This is only a temporary helper class which will be restructured.
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
	public LazySeq<Tuple<K, V>> tail() {
		return underlyingSeq.get().tail();
	}

	@Override
	protected boolean isTailDefined() {
		return underlyingSeq.get().isTailDefined();
	}

	public <R> LazySeq<R> map(Function<? super Tuple<K, V>, ? extends R> mapper) {
		return underlyingSeq.get().map(mapper);
	}

	@Override
	public LazyTupleSeq<K, V> filter(Predicate<? super Tuple<K, V>> predicate) {
		return new LazyTupleSeq<>(underlyingSeq.get().filter(predicate));
	}

	@Override
	public <R> LazySeq<R> flatMap(Function<? super Tuple<K, V>, ? extends Iterable<? extends R>> mapper) {
		return underlyingSeq.get().flatMap(mapper);
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
	public LazyTupleSeq<K, V> sortedBy(Function<Tuple<K, V>, ? extends Comparable> attribute) {
		return sorted(Comparator.comparing(attribute));
	}

	@Override
	protected LazySeq<Tuple<K, V>> takeUnsafe(long maxSize) {
		return underlyingSeq.get().takeUnsafe(maxSize);
	}

	public <R> LazyTupleSeq<R,V> mapKeys(Function<K,R> keyMapper) {
		return new LazyTupleSeq<>(map(t -> new Tuple<>(keyMapper.apply(t._1), t._2)));
	}

	public <R> LazyTupleSeq<K,R> mapValues(Function<V, R> valueMapper) {
		return new LazyTupleSeq<>(map(t -> new Tuple<>(t._1, valueMapper.apply(t._2))));
	}

	public <R> LazySeq<R> map(BiFunction<K, V, R> mapper) {
		return map(t -> mapper.apply(t._1, t._2));
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
}
