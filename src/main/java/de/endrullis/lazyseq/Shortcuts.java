package de.endrullis.lazyseq;

import de.endrullis.lazyseq.function.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;

/**
 * Provides static imports that can be used as shortcuts for LazySeq and its companion classes.
 *
 * @author Stefan Endrullis (stefan@endrullis.de)
 */
@SuppressWarnings("WeakerAccess")
public class Shortcuts {

	/**
	 * Creates an immutable tuple.
	 *
	 * @param <T1> type of the first tuple element
	 * @param <T2> type of the second tuple element
	 * @param t1 first tuple element
	 * @param t2 second tuple element
	 * @return tuple consisting of two elements
	 */
	public static <T1,T2> Tuple<T1,T2> t(T1 t1, T2 t2) {
		return new Tuple<>(t1, t2);
	}

	public static <T1,T2,R> Function<Tuple<T1,T2>,R> tupled(BiFunction<? super T1, ? super T2, ? extends R> function) {
		return t -> function.apply(t._1, t._2);
	}

	public static <T1,T2,R,Ex extends Exception> ExceptionFunction<Tuple<T1,T2>,R,Ex> tupledEx(ExceptionBiFunction<? super T1, ? super T2, ? extends R,Ex> function) {
		return t -> function.apply(t._1, t._2);
	}

	public static <T1,T2> Predicate<Tuple<T1,T2>> tupled(BiPredicate<? super T1, ? super T2> predicate) {
		return t -> predicate.test(t._1, t._2);
	}

	public static <T1,T2,Ex extends Exception> ExceptionPredicate<Tuple<T1,T2>, Ex> tupledEx(ExceptionBiPredicate<? super T1, ? super T2, Ex> predicate) {
		return t -> predicate.test(t._1, t._2);
	}

	public static <T1,T2> Consumer<Tuple<T1,T2>> tupled(BiConsumer<? super T1, ? super T2> consumer) {
		return t -> consumer.accept(t._1, t._2);
	}

	public static <T1,T2,Ex extends Exception> ExceptionConsumer<Tuple<T1,T2>, Ex> tupledEx(ExceptionBiConsumer<? super T1, ? super T2, Ex> consumer) {
		return t -> consumer.accept(t._1, t._2);
	}

	/**
	 * Creates a Java Map based on the given tuples.
	 *
	 * @param <K> type of the keys
	 * @param <V> type of the values
	 * @param tuples tuples used to create the map
	 * @return {@link java.util.HashMap} containing the given tuples
	 */
	@SafeVarargs
	public static <K,V> Map<K,V> jMap(Tuple<K,V>... tuples) {
		HashMap<K, V> map = new HashMap<>();
		for (Tuple<K, V> tuple : tuples) {
			map.put(tuple._1, tuple._2);
		}
		return map;
	}

	/**
	 * Creates a Java List based on the given tuples.
	 * This function is fully equivalent to <code>Arrays.asList(elements)</code>.
	 *
	 * @param <T> type of the elements of the resulting list
	 * @param elements elements of the resulting list
	 * @return {@link java.util.List} containing the given elements
	 */
	@SafeVarargs
	public static <T> List<T> jList(T... elements) {
		return Arrays.asList(elements);
	}

	/**
	 * Creates a Java Set based on the given tuples.
	 *
	 * @param <T> type of the elements of the resulting set
	 * @param elements elements of the resulting set
	 * @return {@link java.util.Set} containing the given elements
	 */
	@SafeVarargs
	public static <T> Set<T> jSet(T... elements) {
		HashSet<T> set = new HashSet<>();
		Collections.addAll(set, elements);
		return set;
	}

	/**
	 * Returns a lazy sequence with the given element.
	 *
	 * @param <T>     element type
	 * @param element element of the new LazySeq
	 * @return lazy sequence with the given element
	 */
	@NotNull
	public static <T> LazySeq<T> ls(@NotNull T element) {
		return LazySeq.of(element);
	}

	/**
	 * Returns a lazy sequence with the given elements.
	 *
	 * @param <T>      element type
	 * @param elements elements of the new LazySeq
	 * @return lazy sequence with the given elements
	 */
	@SafeVarargs
	@NotNull
	public static <T> LazySeq<T> ls(@NotNull T... elements) {
		return LazySeq.of(elements);
	}

	/**
	 * Returns a lazy sequence with the given elements.
	 *
	 * @param <T>      element type
	 * @param elements elements of the new LazySeq
	 * @return lazy sequence with the given elements
	 */
	@NotNull
	public static <T> LazySeq<T> ls(@NotNull Iterable<T> elements) {
		return LazySeq.of(elements);
	}

	/**
	 * Returns a lazy sequence with the given elements.
	 *
	 * @param <T>      element type
	 * @param elements elements of the new LazySeq
	 * @return lazy sequence with the given elements
	 */
	@NotNull
	public static <T> LazySeq<T> ls(@NotNull Iterator<T> elements) {
		return LazySeq.of(elements);
	}

	/**
	 * Returns a lazy tuple sequence with the given tuples.
	 *
	 * @param <K>   key type
	 * @param <V>   value type
	 * @param tuple tuples of the new LazyTupleSeq
	 * @return lazy tuple sequence with the given tuples
	 */
	@NotNull
	public static <K, V> LazyTupleSeq<K, V> lts(@NotNull Tuple<K, V> tuple) {
		return lts(ls(tuple));
	}

	/**
	 * Returns a lazy tuple sequence with the given tuples.
	 *
	 * @param <K>    key type
	 * @param <V>    value type
	 * @param tuples tuples of the new LazyTupleSeq
	 * @return lazy tuple sequence with the given tuples
	 */
	@SafeVarargs
	@NotNull
	public static <K, V> LazyTupleSeq<K, V> lts(@NotNull Tuple<K, V>... tuples) {
		return lts(ls(tuples));
	}

	/**
	 * Returns a lazy tuple sequence with the given tuples.
	 *
	 * @param <K>    key type
	 * @param <V>    value type
	 * @param tuples tuples of the new LazyTupleSeq
	 * @return lazy tuple sequence with the given tuples
	 */
	@NotNull
	public static <K, V> LazyTupleSeq<K, V> lts(@NotNull Iterable<Tuple<K, V>> tuples) {
		return lts(ls(tuples));
	}

	/**
	 * Returns a lazy tuple sequence with the given tuples.
	 *
	 * @param <K>    key type
	 * @param <V>    value type
	 * @param tuples tuples of the new LazyTupleSeq
	 * @return lazy tuple sequence with the given tuples
	 */
	@NotNull
	public static <K, V> LazyTupleSeq<K, V> lts(@NotNull Iterator<Tuple<K, V>> tuples) {
		return lts(ls(tuples));
	}

	/**
	 * Returns a lazy tuple sequence with the given tuples.
	 *
	 * @param <K>    key type
	 * @param <V>    value type
	 * @param tuples tuples of the new LazyTupleSeq
	 * @return lazy tuple sequence with the given tuples
	 */
	@NotNull
	public static <K, V> LazyTupleSeq<K, V> lts(@NotNull LazySeq<Tuple<K, V>> tuples) {
		return new LazyTupleSeq<>(tuples);
	}

	/**
	 * Returns a lazy tuple sequence derived from the given map.
	 *
	 * @param <K> key type
	 * @param <V> value type
	 * @param map map used for building the new LazyTupleSeq
	 * @return lazy tuple sequence derived from the given map
	 */
	@NotNull
	public static <K, V> LazyTupleSeq<K, V> lts(@NotNull Map<K, V> map) {
		return new LazyTupleSeq<>(map);
	}

}
