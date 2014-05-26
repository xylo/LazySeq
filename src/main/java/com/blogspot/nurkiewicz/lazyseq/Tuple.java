package com.blogspot.nurkiewicz.lazyseq;

/**
 * An immutable tuple.
 * Import static function {@link Shortcuts#t} as shortcut for creating tuples.
 *
 * @param <T1> type of the first tuple element
 * @param <T2> type of the second tuple element
 * @author Stefan Endrullis (stefan@endrullis.de)
 */
public class Tuple<T1,T2> {
	/** The first element of this tuple. */
	public final T1 _1;
	/** The second element of this tuple. */
	public final T2 _2;

	/**
	 * Creates an immutable tuple.
	 *
	 * @param t1 first tuple element
	 * @param t2 second tuple element
	 */
	public Tuple(T1 t1, T2 t2) {
		this._1 = t1;
		this._2 = t2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tuple) {
			Tuple that = (Tuple) obj;
			return this._1.equals(that._1) && this._2.equals(that._2);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return _1.hashCode()*31 + _2.hashCode();
	}

	@Override
	public String toString() {
		return "(" + _1 + "," + _2 + ")";
	}
}
