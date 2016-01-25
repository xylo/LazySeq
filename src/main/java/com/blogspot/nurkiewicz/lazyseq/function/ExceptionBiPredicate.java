package com.blogspot.nurkiewicz.lazyseq.function;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Function (A, B) -&gt; boolean that may throw an exception.
 *
 * @author Stefan Endrullis
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface ExceptionBiPredicate<A, B, E extends Exception> {

	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param a the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * otherwise {@code false}
	 */
	boolean test(A a, B b) throws E;

	/**
	 * Returns a composed predicate that represents a short-circuiting logical
	 * AND of this predicate and another.  When evaluating the composed
	 * predicate, if this predicate is {@code false}, then the {@code other}
	 * predicate is not evaluated.
	 * <p>
	 * <p>Any exceptions thrown during evaluation of either predicate are relayed
	 * to the caller; if evaluation of this predicate throws an exception, the
	 * {@code other} predicate will not be evaluated.
	 *
	 * @return a composed predicate that represents the short-circuiting logical
	 * AND of this predicate and the {@code other} predicate
	 * @throws NullPointerException if other is null
	 */
	default ExceptionBiPredicate<A, B, E> and() {return and();}

	/**
	 * Returns a composed predicate that represents a short-circuiting logical
	 * AND of this predicate and another.  When evaluating the composed
	 * predicate, if this predicate is {@code false}, then the {@code other}
	 * predicate is not evaluated.
	 * <p>
	 * <p>Any exceptions thrown during evaluation of either predicate are relayed
	 * to the caller; if evaluation of this predicate throws an exception, the
	 * {@code other} predicate will not be evaluated.
	 *
	 * @param other a predicate that will be logically-ANDed with this
	 *              predicate
	 * @return a composed predicate that represents the short-circuiting logical
	 * AND of this predicate and the {@code other} predicate
	 * @throws NullPointerException if other is null
	 */
	default ExceptionBiPredicate<A, B, E> and(BiPredicate<? super A, ? super B> other) {
		Objects.requireNonNull(other);
		return (a, b) -> test(a, b) && other.test(a, b);
	}

	default ExceptionBiPredicate<A, B, Exception> and(ExceptionBiPredicate<? super A, ? super B, ?> other) {
		Objects.requireNonNull(other);
		return (a, b) -> test(a, b) && other.test(a, b);
	}

	/**
	 * Returns a predicate that represents the logical negation of this
	 * predicate.
	 *
	 * @return a predicate that represents the logical negation of this
	 * predicate
	 */
	default ExceptionBiPredicate<A, B, E> negate() {
		return (a, b) -> !test(a, b);
	}

	/**
	 * Returns a composed predicate that represents a short-circuiting logical
	 * OR of this predicate and another.  When evaluating the composed
	 * predicate, if this predicate is {@code true}, then the {@code other}
	 * predicate is not evaluated.
	 * <p>
	 * <p>Any exceptions thrown during evaluation of either predicate are relayed
	 * to the caller; if evaluation of this predicate throws an exception, the
	 * {@code other} predicate will not be evaluated.
	 *
	 * @param other a predicate that will be logically-ORed with this
	 *              predicate
	 * @return a composed predicate that represents the short-circuiting logical
	 * OR of this predicate and the {@code other} predicate
	 * @throws NullPointerException if other is null
	 */
	default ExceptionBiPredicate<A, B, E> or(BiPredicate<? super A, ? super B> other) {
		Objects.requireNonNull(other);
		return (a, b) -> test(a, b) || other.test(a, b);
	}

	default ExceptionBiPredicate<A, B, Exception> or(ExceptionBiPredicate<? super A, ? super B, ?> other) {
		Objects.requireNonNull(other);
		return (a, b) -> test(a, b) || other.test(a, b);
	}

	/**
	 * Returns a predicate that tests if two arguments are equal according
	 * to {@link Objects#equals(Object, Object)}.
	 *
	 * @param <A>       the type of arguments to the predicate
	 * @param targetRef the object reference with which to compare for equality,
	 *                  which may be {@code null}
	 * @return a predicate that tests if two arguments are equal according
	 * to {@link Objects#equals(Object, Object)}
	 */
	static <A> Predicate<A> isEqual(Object targetRef) {
		return (null == targetRef)
				? Objects::isNull
				: object -> targetRef.equals(object);
	}

}
