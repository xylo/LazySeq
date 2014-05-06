package com.blogspot.nurkiewicz.lazyseq;

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

}
