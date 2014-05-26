package com.blogspot.nurkiewicz.lazyseq;

import java.util.*;

/**
 * Provides static imports that can be used as shortcuts for LazySeq and its companion classes.
 *
 * @author Stefan Endrullis (stefan@endrullis.de)
 */
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

}
