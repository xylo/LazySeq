package de.endrullis.lazyseq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Java 8 implementation of Scala's Some.
 *
 * @author Stefan Endrullis (endrullis@iat.uni-leipzig.de)
 */
@SuppressWarnings("unused")
public class Some<T> implements Option<T> {

	private final T value;

	public Some(T value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Some) {
			Some that = (Some) obj;
			return scalaEquals(this.value, that.value);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}

	@Override
	@NotNull
	public T get() {
		return value;
	}

	@Override
	public T getOrElse(T alternative) {
		return value != null ? value : alternative;
	}

	@Override
	public T getOrElse(@NotNull Supplier<T> alternative) {
		return value != null ? value : alternative.get();
	}

	@Override
	@NotNull
	public T getOrNull() {
		return value;
	}

	@NotNull
	@Override
	public <R> Option<R> map(Function<T, R> f) {
		return Option.apply(f.apply(value));
	}

	@NotNull
	@Override
	public <R> Option<R> flatMap(Function<T, Option<R>> f) {
		return f.apply(value);
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return new SomeIterator();
	}

	@Override
	public void forEach(@NotNull Consumer<? super T> action) {
		action.accept(value);
	}

	@Override
	public Optional<T> toOptional() {
		return Optional.of(value);
	}

	/**
	 * Implements the Scala == function that checks for null values as well.
	 *
	 * @param o1 object 1
	 * @param o2 object 2
	 * @return true if both objects are null, or if both objects are equal to each other
	 */
	protected static boolean scalaEquals(@Nullable Object o1, @Nullable Object o2) {
		return o1 == null && o2 == null || o1 != null && o2 != null && o1.equals(o2);
	}

	class SomeIterator implements Iterator<T> {
		boolean used = false;

		@Override
		public boolean hasNext() {
			return !used;
		}

		@Override
		public T next() {
			if (used) {
				throw new NoSuchElementException("There is only one element in a Some object");
			} else {
				used = true;
				return value;
			}
		}
	}

	@Override
	public String toString() {
		return "Some(" + Objects.toString(value) + ")";
	}
}
