package de.endrullis.lazyseq.function;

import java.util.Objects;

/**
 * Function (A, B) -&gt; {} that may throw an exception.
 *
 * @author Stefan Endrullis
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface ExceptionBiConsumer<A, B, E extends Exception> {

	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param a the first input argument
	 * @param b the second input argument
	 * @throws E exception that might be thrown during the execution
	 */
	void accept(A a, B b) throws E;

	/**
	 * Returns a composed {@code BiConsumer} that performs, in sequence, this
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
	default ExceptionBiConsumer<A, B, Exception> andThen(ExceptionBiConsumer<? super A, ? super B, ? extends Exception> after) {
		Objects.requireNonNull(after);
		return (A a, B b) -> { accept(a, b); after.accept(a, b); };
	}

}
