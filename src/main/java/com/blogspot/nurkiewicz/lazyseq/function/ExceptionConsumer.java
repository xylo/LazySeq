package com.blogspot.nurkiewicz.lazyseq.function;

import java.util.Objects;

/**
 * Function A -&gt; {} that may throw an exception.
 *
 * @author Stefan Endrullis
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface ExceptionConsumer<A, E extends Exception> {

	/**
	 * Performs this operation on the given argument.
	 *
	 * @param a the input argument
	 */
	void accept(A a) throws E;

	/**
	 * Returns a composed {@code Consumer} that performs, in sequence, this
	 * operation followed by the {@code after} operation. If performing either
	 * operation throws an exception, it is relayed to the caller of the
	 * composed operation.  If performing this operation throws an exception,
	 * the {@code after} operation will not be performed.
	 *
	 * @param after the operation to perform after this operation
	 * @return a composed {@code Consumer} that performs in sequence this
	 * operation followed by the {@code after} operation
	 * @throws NullPointerException if {@code after} is null
	 */
	default ExceptionConsumer<A, Exception> andThen(ExceptionConsumer<? super A, ? extends Exception> after) {
		Objects.requireNonNull(after);
		return (A a) -> { accept(a); after.accept(a); };
	}

}
