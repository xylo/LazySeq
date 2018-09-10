package de.endrullis.lazyseq.function;

/**
 * Function () -&gt; O that may throw an exception.
 *
 * @author Stefan Endrullis
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface ExceptionSupplier<O, E extends Exception> {

	/**
	 * Gets a result.
	 *
	 * @return a result
	 * @throws E exception that might be thrown during the execution
	 */
	O get() throws E;

}

