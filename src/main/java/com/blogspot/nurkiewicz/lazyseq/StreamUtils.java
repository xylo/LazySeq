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

	/**
	 * Sequential version of Collectors.toMap which is able to handle null values.
	 *
	 * @param <E>    element type of the input stream
	 * @param <K>    type of the keys
	 * @param <V>    type of the values
	 * @param stream input stream
	 * @param key    function that maps the stream elements to the keys of the resulting map
	 * @param value  function that maps the stream elements to the values of the resulting map
	 * @return a map
	 */
	public static <E, K, V> Map<K, V> toMap(Stream<E> stream, Function<E, K> key, Function<E, V> value) {
		HashMap<K, V> map = new HashMap<>();
		stream.forEach(e -> map.put(key.apply(e), value.apply(e)));
		return map;
	}

}
