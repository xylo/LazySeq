package de.endrullis.lazyseq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static de.endrullis.lazyseq.CastUtils.cast;

/**
 * Java 8 implementation of Scala's Option.
 * It differs from Java 8's Option in the sense that Option does not inherit Supplier and Iterable
 * and does not provide a forEach method.
 *
 * @author Stefan Endrullis (endrullis@iat.uni-leipzig.de)
 */
@SuppressWarnings("unused")
public interface Option<T> extends Supplier<T>, Iterable<T> {

	@NotNull
	Option NONE = new None();

	/**
	 * Returns an empty option (None).
	 * @param <T>  type of the option
	 * @return empty option (None)
	 */
	@NotNull
	static <T> Option<T> empty() {
		//noinspection unchecked
		return cast(NONE);
	}

	/**
	 * Returns an empty option (None).
	 * @param <T>  type of the option
	 * @return empty option (None)
	 */
	@NotNull
	static <T> Option<T> none() {
		//noinspection unchecked
		return cast(NONE);
	}

	/**
	 * Returns Some(value) if the given value is not null or else None.
	 *
	 * @param value nullable value
	 * @param <T>   type of the value
	 * @return Some(value) if the given value is not null or else None
	 */
	@NotNull
	static <T> Option<T> of(@Nullable T value) {
		return apply(value);
	}

	/**
	 * Returns Some(value) if the given value is not null or else None.
	 *
	 * @param value nullable value
	 * @param <T>   type of the value
	 * @return Some(value) if the given value is not null or else None
	 */
	@NotNull
	static <T> Option<T> apply(@Nullable T value) {
		if (value == null) {
			return Option.none();
		} else {
			return new Some<>(value);
		}
	}

	/**
	 * Returns the value of this Option in case of Some(value).
	 * @return value of this Option in case of Some(value)
	 */
	@NotNull
	T get();

	/**
	 * Returns the value of this Option in case of Some(value) or else the given alternative.
	 * @param alternative  alternative value to return in case of None
	 * @return value of this Option in case of Some(value) or else the given alternative
	 */
	T getOrElse(T alternative);

	/**
	 * Returns the value of this Option in case of Some(value) or else the given alternative.
	 * @param alternative  alternative value to return in case of None
	 * @return value of this Option in case of Some(value) or else the given alternative
	 */
	T getOrElse(@NotNull Supplier<T> alternative);

	/**
	 * Returns the value of this Option in case of Some(value) or else null.
	 * @return value of this Option in case of Some(value) or else null
	 */
	@Nullable
	T getOrNull();

	/**
	 * Returns this Option in case of Some(value) or else the given alternative as Option.
	 * @param alternative  alternative value to return as Option in case of None
	 * @return this Option in case of Some(value) or else the given alternative as Option
	 */
	Option<T> orElse(@Nullable T alternative);

	/**
	 * Returns this Option in case of Some(value) or else the given alternative as Option.
	 * @param alternative  alternative value to return as Option in case of None
	 * @return this Option in case of Some(value) or else the given alternative as Option
	 */
	Option<T> orElse(@NotNull Supplier<T> alternative);

	@NotNull
	<R> Option<R> map(Function<T, R> f);

	@NotNull
	<R> Option<R> flatMap(Function<T, Option<R>> f);

	@NotNull
	Option<T> filter(@NotNull Predicate<? super T> predicate);

	/**
	 * Returns true if this option has a value.
	 * @return true if this option has a value
	 */
	boolean isDefined();

	/**
	 * Returns true if this option has a value.
	 * @return true if this option has a value
	 */
	default boolean isPresent() {
		return isDefined();
	}

	/**
	 * Returns true if this option is empty (has no value).
	 * @return true if this option is empty (has no value)
	 */
	default boolean isEmpty() {
		return !isDefined();
	}

	/**
	 * Performs the given action on the Option value if this Option is Some(value).
	 * In case of None the action is ignored.
	 * @param action  action to perform in case of Some(value)
	 */
	void forEach(@NotNull Consumer<? super T> action);

	/**
	 * Returns a Java Optional of this Option.
	 *
	 * @return Java Optional of this Option
	 */
	Optional<T> toOptional();

	/**
	 * Returns a list containing one element in case of Some(element) or an empty list in case of None.
	 * @return list with a single element if Some, or else an empty list
	 */
	default LazySeq<T> toList() {
		return LazySeq.of(iterator());
	}

}
