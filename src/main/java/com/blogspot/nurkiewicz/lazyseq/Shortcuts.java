package com.blogspot.nurkiewicz.lazyseq;

import java.util.*;

/**
 * Provides static imports that can be used as shortcuts for LazySeq and its companion classes.
 *
 * @author Stefan Endrullis (endrullis@iat.uni-leipzig.de)
 */
public class Shortcuts {

	/** Creates an immutable tuple. */
	public static <T1,T2> Tuple<T1,T2> t(T1 t1, T2 t2) {
		return new Tuple<>(t1, t2);
	}

	/** Creates a Java Map based on the given tuples. */
	@SafeVarargs
	public static <K,V> Map<K,V> jMap(Tuple<K,V>... tuples) {
		HashMap<K, V> map = new HashMap<>();
		for (Tuple<K, V> tuple : tuples) {
			map.put(tuple._1, tuple._2);
		}
		return map;
	}

	/** Creates a Java List based on the given tuples. */
	@SafeVarargs
	public static <T> List<T> jList(T... elements) {
		return Arrays.asList(elements);
	}

	/** Creates a Java Set based on the given tuples. */
	@SafeVarargs
	public static <T> Set<T> jSet(T... elements) {
		HashSet<T> set = new HashSet<>();
		Collections.addAll(set, elements);
		return set;
	}

}
