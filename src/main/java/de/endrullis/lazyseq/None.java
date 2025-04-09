package de.endrullis.lazyseq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Java 8 implementation of Scala's None.
 *
 * @author Stefan Endrullis (endrullis@iat.uni-leipzig.de)
 */
@SuppressWarnings("unused")
public class None implements Option {

	@Override
	@NotNull
	public Object get() {
		throw new NoSuchElementException();
	}

	@Override
	public Object getOrElse(Object alternative) {
		return alternative;
	}

	@Override
	public Object getOrElse(@NotNull Supplier alternative) {
		return alternative.get();
	}

	@Override
	@Nullable
	public Object getOrNull() {
		return null;
	}

	@Override
	public Option orElse(@Nullable Object alternative) {
		return Option.of(alternative);
	}

	@Override
	public Option orElse(@NotNull Supplier alternative) {
		return Option.of(alternative.get());
	}

	@NotNull
	@Override
	public Option map(@NotNull Function f) {
		return Option.NONE;
	}

	@NotNull
	@Override
	public Option flatMap(@NotNull Function f) {
		return Option.NONE;
	}

	@NotNull
	@Override
	public Option filter(@NotNull Predicate predicate) {
		return Option.NONE;
	}

	@Override
	public boolean isDefined() {
		return false;
	}

	@Override
	public Iterator iterator() {
		return new SomeIterator();
	}

	@Override
	public void forEach(@NotNull Consumer action) {
	}

	@Override
	public Optional toOptional() {
		return Optional.empty();
	}

	class SomeIterator implements Iterator {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Object next() {
			throw new NoSuchElementException("There is no element in a None object");
		}
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof None;
	}

	@Override
	public String toString() {
		return "None";
	}
}
