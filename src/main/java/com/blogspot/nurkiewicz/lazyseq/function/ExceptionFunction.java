package com.blogspot.nurkiewicz.lazyseq.function;

/**
 * Function A -&gt; R that may throw an exception.
 *
 * @author Stefan Endrullis
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface ExceptionFunction<A, R, E extends Exception> {

	R apply(A a) throws E;

}
