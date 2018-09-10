package de.endrullis.lazyseq;

/**
 * Utilities for avoiding cast warnings.
 *
 * @author Stefan Endrullis
 */
class CastUtils {

	/**
	 * Use this function to eliminate silly "unchecked" warnings that cannot be fixed in Java otherwise.
	 *
	 * @param x   value to cast
	 * @param <T> type to cast to
	 * @return same value
	 */
	@SuppressWarnings("unchecked")
	static <T> T cast(Object x) {
		return (T) x;
	}

}
