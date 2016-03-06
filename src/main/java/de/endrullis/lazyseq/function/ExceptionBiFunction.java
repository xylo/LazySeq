package de.endrullis.lazyseq.function;

import java.util.Objects;

/**
 * Function (A, B) -&gt; R that may throw an exception.
 *
 * @author Stefan Endrullis
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface ExceptionBiFunction<T, U, R, E extends Exception> {

	/**
	 * Applies this function to the given arguments.
	 *
	 * @param t the first function argument
	 * @param u the second function argument
	 * @return the function result
	 */
	R apply(T t, U u) throws E;

	/**
	 * Returns a composed function that first applies this function to
	 * its input, and then applies the {@code after} function to the result.
	 * If evaluation of either function throws an exception, it is relayed to
	 * the caller of the composed function.
	 *
	 * @param <V> the type of output of the {@code after} function, and of the
	 *           composed function
	 * @param after the function to apply after this function is applied
	 * @return a composed function that first applies this function and then
	 * applies the {@code after} function
	 * @throws NullPointerException if after is null
	 */
	default <V> ExceptionBiFunction<T, U, V, Exception> andThen(ExceptionFunction<? super R, ? extends V, ? extends Exception> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> after.apply(apply(t, u));
	}

}
