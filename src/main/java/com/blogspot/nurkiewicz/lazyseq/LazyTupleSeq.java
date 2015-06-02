package com.blogspot.nurkiewicz.lazyseq;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.*;

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

	@NotNull
	@Override
	public Tuple<K, V> head() {
		return underlyingSeq.get().head();
	}

	@NotNull
	@Override
	public Optional<Tuple<K, V>> headOption() {
		if (empty) {
			return Optional.empty();
		} else {
			return super.headOption();
		}
	}

	@NotNull
	@Override
	public LazySeq<Tuple<K, V>> tail() {
		return underlyingSeq.get().tail();
	}

	@Override
	protected boolean isTailDefined() {
		return underlyingSeq.get().isTailDefined();
	}

	@NotNull
	@Override
	public <R> LazySeq<R> map(@NotNull Function<? super Tuple<K, V>, ? extends R> mapper) {
		return underlyingSeq.get().map(mapper);
	}

	@NotNull
	public <R> LazySeq<R> map(BiFunction<K, V, R> mapper) {
		return map(tupled(mapper));
	}

	@NotNull
	@Override
	public <R> LazySeq<R> flatMap(@NotNull Function<? super Tuple<K, V>, ? extends Iterable<? extends R>> mapper) {
		return underlyingSeq.get().flatMap(mapper);
	}

	@NotNull
	public <R> LazySeq<R> flatMap(BiFunction<? super K, ? super V, ? extends Iterable<? extends R>> mapper) {
		return flatMap(tupled(mapper));
	}

	@NotNull
	@Override
	public LazyTupleSeq<K, V> filter(@NotNull Predicate<? super Tuple<K, V>> predicate) {
		return new LazyTupleSeq<>(underlyingSeq.get().filter(predicate));
	}

	@NotNull
	public LazyTupleSeq<K, V> filter(BiPredicate<? super K, ? super V> predicate) {
		return filter(tupled(predicate));
	}

	@NotNull
	@Override
	public LazyTupleSeq<K,V> takeWhile(@NotNull Predicate<? super Tuple<K, V>> predicate) {
		return new LazyTupleSeq<>(underlyingSeq.get().takeWhile(predicate));
	}

	@NotNull
	public LazyTupleSeq<K,V> takeWhile(BiPredicate<? super K, ? super V> predicate) {
		return takeWhile(tupled(predicate));
	}

	@NotNull
	@Override
	public LazyTupleSeq<K,V> dropWhile(@NotNull Predicate<? super Tuple<K, V>> predicate) {
		return new LazyTupleSeq<>(underlyingSeq.get().dropWhile(predicate));
	}

	@NotNull
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

	@NotNull
	@Override
	public LazyTupleSeq<K, V> sorted() {
		return sorted((o1, o2) -> ((Comparable<Tuple<K, V>>) o1).compareTo(o2));
	}

	@NotNull
	@Override
	public LazyTupleSeq<K, V> sorted(@NotNull Comparator<? super Tuple<K, V>> comparator) {
		return new LazyTupleSeq<>(super.sorted(comparator));
	}

	@NotNull
	@Override
	public LazyTupleSeq<K, V> sortedBy(@NotNull Function<? super Tuple<K, V>, ? extends Comparable> attribute) {
		return sorted(Comparator.comparing(attribute));
	}

	@NotNull
	public LazyTupleSeq<K, V> sortedBy(BiFunction<? super K, ? super V, ? extends Comparable> attribute) {
		return sortedBy(tupled(attribute));
	}

	@NotNull
	@Override
	protected LazySeq<Tuple<K, V>> takeUnsafe(long maxSize) {
		return underlyingSeq.get().takeUnsafe(maxSize);
	}

	@NotNull
	public <R> LazyTupleSeq<R,V> mapKeys(Function<? super K, ? extends R> keyMapper) {
		return new LazyTupleSeq<>(map(t -> new Tuple<>(keyMapper.apply(t._1), t._2)));
	}

	@NotNull
	public <R> LazyTupleSeq<K,R> mapValues(Function<? super V, ? extends R> valueMapper) {
		return new LazyTupleSeq<>(map(t -> new Tuple<>(t._1, valueMapper.apply(t._2))));
	}

	public void forEach(BiConsumer<? super K, ? super V> action) {
		forEach(tupled(action));
	}

	@NotNull
	public <C extends Comparable<? super C>> Optional<Tuple<K,V>> maxBy(BiFunction<? super K, ? super V, C> property) {
		return maxBy(tupled(property));
	}

	@NotNull
	public <C extends Comparable<? super C>> Optional<Tuple<K,V>> minBy(BiFunction<? super K, ? super V, C> property) {
		return minBy(tupled(property));
	}

	@NotNull
	public LazySeq<K> keys() {
		return underlyingSeq.get().map(t -> t._1);
	}

	@NotNull
	public LazySeq<V> values() {
		return underlyingSeq.get().map(t -> t._2);
	}

	/**
	 * Returns a new lazy tuple seq with swapped tuples (_1 and _2 swapped).
	 *
	 * @return new lazy tuple seq with swapped tuples (_1 and _2 swapped)
	 */
	 @NotNull
	public LazyTupleSeq<V, K> swap() {
		return new LazyTupleSeq<>(map(t -> t.swap()));
	}

	@NotNull
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

	@NotNull
	@Override
	public <S, R> LazySeq<R> zip(@NotNull LazySeq<? extends S> second, @NotNull BiFunction<? super Tuple<K, V>, ? super S, ? extends R> zipper) {
		if (this.isEmpty()) {
			return empty();
		} else {
			return super.zip(second, zipper);
		}
	}

}
