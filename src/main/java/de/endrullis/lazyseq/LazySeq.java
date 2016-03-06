package de.endrullis.lazyseq;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.endrullis.lazyseq.Shortcuts.t;

/**
 * @author Tomasz Nurkiewicz
 * @since 5/6/13, 9:20 PM
 */
public abstract class LazySeq<E> extends AbstractList<E> {

	@SuppressWarnings("unchecked")
	public static <E> LazySeq<E> empty() {
		return Nil.instance();
	}

	@NotNull
	public abstract E head();

	@NotNull
	public Option<E> headOption() {
		return Option.of(head());
	}

	@NotNull
	public abstract LazySeq<E> tail();

	@NotNull
	public static <E> LazySeq<E> of(@NotNull E element) {
		return cons(element, empty());
	}

	@NotNull
	public static <E> LazySeq<E> of(@NotNull E element, @NotNull Supplier<LazySeq<E>> tailFun) {
		return cons(element, tailFun);
	}

	@NotNull
	public static <E> LazySeq<E> of(@NotNull E element1, @NotNull E element2) {
		return cons(element1, of(element2));
	}

	@NotNull
	public static <E> LazySeq<E> of(@NotNull E element1, @NotNull E element2, @NotNull Supplier<LazySeq<E>> tailFun) {
		return cons(element1, of(element2, tailFun));
	}

	@NotNull
	public static <E> LazySeq<E> of(@NotNull E element1, @NotNull E element2, @NotNull E element3) {
		return cons(element1, of(element2, element3));
	}

	@NotNull
	public static <E> LazySeq<E> of(@NotNull E element1, @NotNull E element2, @NotNull E element3, @NotNull Supplier<LazySeq<E>> tailFun) {
		return cons(element1, of(element2, element3, tailFun));
	}

	@NotNull
	@SafeVarargs
	public static <E> LazySeq<E> of(@NotNull E... elements) {
		return of(Arrays.asList(elements).iterator());
	}

	@NotNull
	public static <E> LazySeq<E> of(@NotNull Iterable<E> elements) {
		return of(elements.iterator());
	}

	@NotNull
	public static <E> LazySeq<E> of(@NotNull Iterator<E> iterator) {
		if (iterator.hasNext()) {
			return cons(iterator.next(), () -> of(iterator));
		} else {
			return empty();
		}
	}

	@NotNull
	public static <E> LazySeq<E> concat(@NotNull Iterable<E> elements, @NotNull Supplier<LazySeq<E>> tailFun) {
		return concat(elements.iterator(), tailFun);
	}

	@NotNull
	public static <E> LazySeq<E> concat(@NotNull Iterable<E> elements, @NotNull LazySeq<E> tail) {
		return concat(elements.iterator(), tail);
	}

	@NotNull
	public static <E> LazySeq<E> concat(@NotNull Iterator<E> iterator, @NotNull LazySeq<E> tail) {
		if (iterator.hasNext()) {
			return concatNonEmptyIterator(iterator, tail);
		} else {
			return tail;
		}
	}

	@NotNull
	public static <E> LazySeq<E> concat(@NotNull Iterator<E> iterator, @NotNull Supplier<LazySeq<E>> tailFun) {
		if (iterator.hasNext()) {
			return concatNonEmptyIterator(iterator, tailFun);
		} else {
			return tailFun.get();
		}
	}

	@NotNull
	private static <E> LazySeq<E> concatNonEmptyIterator(@NotNull Iterator<E> iterator, @NotNull LazySeq<E> tail) {
		final E next = iterator.next();
		if (iterator.hasNext()) {
			return cons(next, concatNonEmptyIterator(iterator, tail));
		} else {
			return cons(next, tail);
		}
	}

	@NotNull
	private static <E> LazySeq<E> concatNonEmptyIterator(@NotNull Iterator<E> iterator, @NotNull Supplier<LazySeq<E>> tailFun) {
		final E next = iterator.next();
		if (iterator.hasNext()) {
			return cons(next, concatNonEmptyIterator(iterator, tailFun));
		} else {
			return cons(next, tailFun);
		}
	}

	@NotNull
	public static <E> LazySeq<E> cons(@NotNull E head, @NotNull Supplier<LazySeq<E>> tailFun) {
		return new Cons<>(head, tailFun);
	}

	@NotNull
	public static <E> LazySeq<E> cons(@NotNull E head, @NotNull LazySeq<E> tail) {
		return new FixedCons<>(head, tail);
	}

	@NotNull
	public static <E> LazySeq<E> iterate(@NotNull E initial, @NotNull Function<E, E> fun) {
		return new Cons<>(initial, () -> iterate(fun.apply(initial), fun));
	}

	@NotNull
	public static <E> Collector<E, LazySeq<E>, LazySeq<E>> toLazySeq() {
		return DummyLazySeqCollector.getInstance();
	}

	@NotNull
	public static <E> LazySeq<E> tabulate(int start, @NotNull Function<Integer, E> generator) {
		return cons(generator.apply(start), () -> tabulate(start + 1, generator));
	}

	@NotNull
	public static <E> LazySeq<E> continually(@NotNull Supplier<E> generator) {
		return cons(generator.get(), () -> continually(generator));
	}

	@NotNull
	public static <E> LazySeq<E> continually(@NotNull Iterable<E> cycle) {
		if (!cycle.iterator().hasNext()) {
			return empty();
		}
		return continuallyUnsafe(cycle);
	}

	@NotNull
	private static <E> LazySeq<E> continuallyUnsafe(@NotNull Iterable<E> cycle) {
		return concat(cycle, () -> continually(cycle));
	}

	@NotNull
	public static <E> LazySeq<E> continually(@NotNull E value) {
		return cons(value, () -> continually(value));
	}

	@NotNull
	public static LazySeq<Integer> numbers(int start) {
		return numbers(start, 1);
	}

	@NotNull
	public static LazySeq<Integer> numbers(int start, int step) {
		return cons(start, () -> numbers(start + step, step));
	}

	@NotNull
	public static LazySeq<Double> numbers(double start) {
		return numbers(start, 1.0);
	}

	@NotNull
	public static LazySeq<Double> numbers(double start, double step) {
		return cons(start, () -> numbers(start + step, step));
	}

	@NotNull
	public LazySeq<E> concat(@NotNull LazySeq<E> seq) {
		return concat(this, seq);
	}

	protected abstract boolean isTailDefined();

	@Override
	@NotNull
	public E get(final int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		LazySeq<E> cur = this;
		for (int curIdx = index; curIdx > 0; --curIdx) {
			if (cur.tail().isEmpty()) {
				throw new IndexOutOfBoundsException(Integer.toString(index));
			}
			cur = cur.tail();
		}
		return cur.head();
	}

	@NotNull
	public abstract <R> LazySeq<R> map(@NotNull Function<? super E, ? extends R> mapper);

	@Override
	@NotNull
	public Stream<E> stream() {
		return new LazySeqStream<>(this);
	}

	@Override
	@NotNull
	public Stream<E> parallelStream() {
		return stream();
	}

	@Override
	@NotNull
	public String toString() {
		return mkString("[", ", ", "]", true);
	}

	/**
	 * Returns a string representation of this {@link LazySeq} by concatenating the elements
	 * using the separator <code>sep</code>.
	 *
	 * @param sep separator
	 * @return string representation of this {@link LazySeq} by concatenating the elements
	 */
	@NotNull
	public String mkString(@NotNull String sep) {
		return mkString("", sep, "");
	}

	/**
	 * Returns a string representation of this {@link LazySeq}.
	 * The string result starts with the string <code>start</code> and ends with <code>end</code>,
	 * whereas <code>sep</code> is as separator string between the elements of this sequence.
	 *
	 * @param start string that is added in front of the element concatenation
	 * @param sep   separator
	 * @param end   string that is appended to the element concatenation
	 * @return string representation of this {@link LazySeq} by concatenating the elements
	 */
	@NotNull
	public String mkString(@NotNull String start, @NotNull String sep, @NotNull String end) {
		return mkString(start, sep, end, false);
	}

	/**
	 * Returns a string representation of this {@link LazySeq}.
	 * The string result starts with the string <code>start</code> and ends with <code>end</code>,
	 * whereas <code>sep</code> is as separator string between the elements of this sequence.
	 * If <code>lazy</code> is <code>true</code> the evaluation stops at the first unspecific element.
	 *
	 * @param start string that is added in front of the element concatenation
	 * @param sep   separator
	 * @param end   string that is appended to the element concatenation
	 * @param lazy  if <code>true</code> the evaluation stops at the first unspecific element
	 * @return string representation of this {@link LazySeq} by concatenating the elements
	 */
	@NotNull
	public String mkString(@NotNull String start, @NotNull String sep, @NotNull String end, boolean lazy) {
		final StringBuilder s = new StringBuilder(start);
		LazySeq<E> cur = this;
		while (!cur.isEmpty()) {
			s.append(cur.head());
			if (!lazy || cur.isTailDefined()) {
				if (!cur.tail().isEmpty()) {
					s.append(sep);
				}
				cur = cur.tail();
			} else {
				s.append(sep).append("?");
				break;
			}
		}
		return s.append(end).toString();
	}

	@NotNull
	public LazySeq<E> filter(@NotNull Predicate<? super E> predicate) {
		LazySeq<E> curr = this;
		while (!curr.isEmpty() && !predicate.test(curr.head())) {
			curr = curr.tail();
		}

		if (!curr.isEmpty()) {
			final LazySeq<E> finalCurr = curr;
			return cons(curr.head(), () -> finalCurr.tail().filter(predicate));
		} else {
			return empty();
		}
	}

	@NotNull
	public Option<E> find(@NotNull Predicate<? super E> predicate) {
		return filter(predicate).headOption();
	}

	@NotNull
	public abstract <R> LazySeq<R> flatMap(@NotNull Function<? super E, ? extends Iterable<? extends R>> mapper);

	@NotNull
	public LazySeq<E> limit(long maxSize) {
		return take(maxSize);
	}

	/**
	 * <p>Converts this {@link LazySeq} to immutable {@link List}.</p>
	 * <p>Notice that this method will eventually fail at runtime when called on infinite sequence.</p>
	 *
	 * @return {@link List} of all elements in this lazy sequence.
	 */
	@NotNull
	public List<E> toList() {
		return Collections.unmodifiableList(this.force());
	}

	/**
	 * <p>Converts this {@link LazySeq} to immutable {@link Set}.</p>
	 * <p>Notice that this method will eventually fail at runtime when called on infinite sequence.</p>
	 *
	 * @return {@link Set} of all elements in this lazy sequence.
	 */
	@NotNull
	public Set<E> toSet() {
		return Collections.unmodifiableSet(new HashSet<>(this.force()));
	}

	/**
	 * <p>Converts this {@link LazySeq} to an immutable {@link Map} using the given key and value functions.</p>
	 * <p>Notice that this method will eventually fail at runtime when called on infinite sequence.</p>
	 *
	 * @param <K>   type of the keys
	 * @param <V>   type of the values
	 * @param key   function that returns the key for an element
	 * @param value function that returns the value for an element
	 * @return {@link Map} of all elements in this lazy sequence.
	 */
	@NotNull
	public <K, V> Map<K, V> toMap(Function<E, K> key, Function<E, V> value) {
		return Collections.unmodifiableMap(StreamUtils.toMap(this.force().stream(), key, value));
	}

	/**
	 * Groups this {@link LazySeq} using the given key function and returns the {@link LazyTupleSeq}.
	 *
	 * @param <K> type of the keys
	 * @param key function that returns the key for an element used for grouping the elements
	 * @return lazy sequence of tuples
	 */
	@NotNull
	public <K> LazyTupleSeq<K, List<E>> groupBy(Function<E, K> key) {
		return new LazyTupleSeq<>(this.force().stream().collect(Collectors.groupingBy(key)));
	}

	/**
	 * Groups this {@link LazySeq} using the given key and value functions and returns the {@link LazyTupleSeq}.
	 *
	 * @param <K>   type of the keys
	 * @param <V>   type of the values
	 * @param key   function that returns the key for an element used for grouping the elements
	 * @param value function that returns the value for an element while grouping
	 * @return lazy sequence of tuples
	 */
	@NotNull
	public <K, V> LazyTupleSeq<K, List<V>> groupBy(Function<E, K> key, Function<E, V> value) {
		return groupBy(key, value, Collectors.toList());
	}

	/**
	 * Groups this {@link LazySeq} using the given key and value functions and returns the {@link LazyTupleSeq}.
	 *
	 * @param <K>            type of the keys
	 * @param <V>            type of the values
	 * @param <R>            type of the values collection
	 * @param key            function that returns the key for an element used for grouping the elements
	 * @param value          function that returns the value for an element while grouping
	 * @param valueCollector collector for the values of the elements
	 * @return lazy sequence of tuples
	 */
	@NotNull
	public <K, V, R> LazyTupleSeq<K, R> groupBy(Function<E, K> key, Function<E, V> value, Collector<V, ?, R> valueCollector) {
		return groupBy(key, Collectors.mapping(value, valueCollector));
	}

	/**
	 * Groups this {@link LazySeq} using the given key and returns the {@link LazyTupleSeq}.
	 *
	 * @param <K>              type of the keys
	 * @param <V>              type of the values
	 * @param key              function that returns the key for an element used for grouping the elements
	 * @param elementCollector collector for the elements
	 * @return lazy sequence of tuples
	 */
	@NotNull
	public <K, V> LazyTupleSeq<K, V> groupBy(Function<E, K> key, Collector<E, ?, V> elementCollector) {
		return new LazyTupleSeq<>(this.force().stream().collect(Collectors.groupingBy(key, elementCollector)));
	}

	@NotNull
	public LazySeq<E> take(long maxSize) {
		if (maxSize < 0) {
			throw new IllegalArgumentException(Long.toString(maxSize));
		}
		if (maxSize == 0) {
			return empty();
		}
		return takeUnsafe(maxSize);
	}

	@NotNull
	protected abstract LazySeq<E> takeUnsafe(long maxSize);

	@NotNull
	public LazySeq<E> drop(long startInclusive) {
		if (startInclusive < 0) {
			throw new IllegalArgumentException(Long.toString(startInclusive));
		}
		return dropUnsafe(startInclusive);
	}

	/**
	 * Procedural implementation of dropUnsafeFunctional (which causes StackOverflows).
	 *
	 * @param startInclusive number of elements to drop from this LazySeq
	 * @return LazySeq without the first n (`startInclusive`) elements
	 */
	protected LazySeq<E> dropUnsafe(long startInclusive) {
		LazySeq<E> seq = this;
		for (; startInclusive > 0 && !seq.isEmpty(); startInclusive--) {
			seq = seq.tail();
		}
		return seq;
	}

	/*
	@Deprecated
	protected LazySeq<E> dropUnsafeFunctional(long startInclusive) {
		if (startInclusive > 0) {
			return tail().drop(startInclusive - 1);
		} else {
			return this;
		}
	}
	*/

	@NotNull
	@Override
	public LazySeq<E> subList(int fromIndex, int toIndex) {
		return slice(fromIndex, toIndex);
	}

	@NotNull
	public LazySeq<E> slice(long startInclusive, long endExclusive) {
		if (startInclusive < 0 || startInclusive > endExclusive) {
			throw new IllegalArgumentException("slice(" + startInclusive + ", " + endExclusive + ")");
		}
		return dropUnsafe(startInclusive).takeUnsafe(endExclusive - startInclusive);
	}

	public void forEach(@NotNull Consumer<? super E> action) {
		LazySeq<E> cur = this;
		while (!cur.isEmpty()) {
			action.accept(cur.head());
			cur = cur.tail();
		}
	}

	@NotNull
	public Option<E> reduce(@NotNull BinaryOperator<E> accumulator) {
		if (isEmpty() || tail().isEmpty()) {
			return Option.empty();
		}
		return Option.of(tail().reduce(head(), accumulator));
	}

	@NotNull
	public <U> U reduce(@NotNull U identity, @NotNull BiFunction<U, ? super E, U> accumulator) {
		U result = identity;
		LazySeq<E> cur = this;
		while (!cur.isEmpty()) {
			result = accumulator.apply(result, cur.head());
			cur = cur.tail();
		}
		return result;
	}

	@NotNull
	public <C extends Comparable<? super C>> Option<E> maxBy(@NotNull Function<? super E, C> property) {
		return max(Comparator.comparing(property));
	}

	@NotNull
	public Option<E> max(@NotNull Comparator<? super E> comparator) {
		return greatestByComparator(comparator);
	}

	@NotNull
	public <C extends Comparable<? super C>> Option<E> minBy(@NotNull Function<? super E, C> property) {
		return min(Comparator.comparing(property));
	}

	@NotNull
	public Option<E> min(@NotNull Comparator<? super E> comparator) {
		return greatestByComparator(comparator.reversed());
	}

	@NotNull
	private Option<E> greatestByComparator(@NotNull Comparator<? super E> comparator) {
		if (tail().isEmpty()) {
			return Option.of(head());
		}
		E minSoFar = head();
		LazySeq<E> cur = this.tail();
		while (!cur.isEmpty()) {
			minSoFar = maxByComparator(minSoFar, cur.head(), comparator);
			cur = cur.tail();
		}
		return Option.of(minSoFar);
	}

	@NotNull
	private static <E> E maxByComparator(@NotNull E first, @NotNull E second, Comparator<? super E> comparator) {
		return comparator.compare(first, second) >= 0 ? first : second;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int size() {
		LazySeq<E> seq = this;
		int size = 0;
		while (!seq.isEmpty()) {
			size++;
			seq = seq.tail();
		}
		return size;
	}

	@Override
	@NotNull
	public Iterator<E> iterator() {
		return new LazySeqIterator<>(this);
	}

	public boolean anyMatch(@NotNull Predicate<? super E> predicate) {
		return predicate.test(head()) || tail().anyMatch(predicate);
	}

	public boolean allMatch(@NotNull Predicate<? super E> predicate) {
		return predicate.test(head()) && tail().allMatch(predicate);
	}

	public boolean noneMatch(@NotNull Predicate<? super E> predicate) {
		return allMatch(predicate.negate());
	}

	public boolean exists(@NotNull Predicate<? super E> predicate) {
		return anyMatch(predicate);
	}

	public boolean forall(@NotNull Predicate<? super E> predicate) {
		return allMatch(predicate);
	}

	@NotNull
	public <S, R> LazySeq<R> zip(@NotNull LazySeq<? extends S> second, @NotNull BiFunction<? super E, ? super S, ? extends R> zipper) {
		if (second.isEmpty()) {
			return empty();
		} else {
			final R headsZipped = zipper.apply(head(), second.head());
			return cons(headsZipped, () -> tail().zip(second.tail(), zipper));
		}
	}

	@NotNull
	public <S> LazyTupleSeq<E, S> zip(@NotNull LazySeq<? extends S> second) {
		return new LazyTupleSeq<>(zip(second, (a, b) -> t(a, b)));
	}

	@NotNull
	public LazyTupleSeq<E, Integer> zipWithIndex() {
		return zipWithIndex(0);
	}

	@NotNull
	public LazyTupleSeq<E, Integer> zipWithIndex(int startIndex) {
		return zip(numbers(startIndex));
	}

	@NotNull
	public LazySeq<E> takeWhile(@NotNull Predicate<? super E> predicate) {
		if (predicate.test(head())) {
			return cons(head(), () -> tail().takeWhile(predicate));
		} else {
			return empty();
		}
	}

	@NotNull
	public LazySeq<E> dropWhile(@NotNull Predicate<? super E> predicate) {
		if (predicate.test(head())) {
			return tail().dropWhile(predicate);
		} else {
			return this;
		}
	}

	@NotNull
	public LazySeq<List<E>> sliding(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException(Integer.toString(size));
		}
		return slidingUnsafe(size);
	}

	@NotNull
	protected LazySeq<List<E>> slidingUnsafe(int size) {
		final List<E> window = take(size).toList();
		return cons(window, () -> tail().slidingFullOnly(size));
	}

	@NotNull
	protected LazySeq<List<E>> slidingFullOnly(int size) {
		final List<E> window = take(size).toList();
		if (window.size() < size) {
			return empty();
		} else {
			return cons(window, () -> tail().slidingFullOnly(size));
		}
	}

	@NotNull
	public LazySeq<List<E>> grouped(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException(Integer.toString(size));
		}
		return groupedUnsafe(size);
	}

	@NotNull
	protected LazySeq<List<E>> groupedUnsafe(int size) {
		final List<E> window = take(size).toList();
		return cons(window, () -> drop(size).groupedUnsafe(size));
	}

	@NotNull
	public LazySeq<E> scan(@NotNull E initial, @NotNull BinaryOperator<E> fun) {
		return cons(initial, () -> tail().scan(fun.apply(initial, head()), fun));
	}

	@NotNull
	public LazySeq<E> distinct() {
		return filterOutSeen(new HashSet<>());
	}

	@NotNull
	private LazySeq<E> filterOutSeen(@NotNull Set<E> exclude) {
		final LazySeq<E> moreDistinct = filter(e -> !exclude.contains(e));
		if (moreDistinct.isEmpty()) {
			return empty();
		}
		final E next = moreDistinct.head();
		exclude.add(next);
		return cons(next, () -> moreDistinct.tail().filterOutSeen(exclude));
	}

	@SuppressWarnings("unchecked")
	@NotNull
	public LazySeq<E> sorted() {
		return sorted((o1, o2) -> ((Comparable<E>) o1).compareTo(o2));
	}

	@NotNull
	public LazySeq<E> sorted(@NotNull Comparator<? super E> comparator) {
		final ArrayList<E> list = new ArrayList<>(this);
		list.sort(comparator);
		return of(list);
	}

	/**
	 * Sorts this {@link LazySeq} regarding the given property.
	 *
	 * @param property function that maps the elements to the property the sequence shall be sorted on
	 * @return sorted {@link LazySeq}
	 */
	@NotNull
	public LazySeq<E> sortedBy(@NotNull Function<? super E, ? extends Comparable> property) {
		return sorted(Comparator.comparing(property));
	}

	public boolean startsWith(Iterable<E> prefix) {
		return startsWith(prefix.iterator());
	}

	public boolean startsWith(Iterator<E> iterator) {
		return !iterator.hasNext() ||
				head().equals(iterator.next()) && tail().startsWith(iterator);
	}

	@NotNull
	public LazySeq<E> force() {
		LazySeq<E> curr = this;
		while (!curr.isEmpty()) {
			curr = curr.tail();
		}
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LazySeq)) return false;

		LazySeq right = (LazySeq) o;
		return !right.isEmpty() &&
				head().equals(right.head()) &&
				tail().equals(right.tail());
	}

	@Override
	public int hashCode() {
		return head().hashCode() + tail().hashCode() * 31;
	}

}


