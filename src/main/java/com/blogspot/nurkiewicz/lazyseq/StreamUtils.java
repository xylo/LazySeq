package com.blogspot.nurkiewicz.lazyseq;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Utilities for Java Streams.
 *
 * @author Stefan Endrullis (endrullis@iat.uni-leipzig.de)
 */
public class StreamUtils {

	/** Sequential version of Collectors.toMap which is able to handle null values. */
	public static <E, K,V> Map<K,V> toMap(Stream<E> stream, Function<E, K> key, Function<E, V> value) {
		HashMap<K,V> map = new HashMap<>();
		stream.forEach(e -> map.put(key.apply(e), value.apply(e)));
		return map;
	}

}
