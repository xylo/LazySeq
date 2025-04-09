package de.endrullis.lazyseq;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.*;

import static de.endrullis.lazyseq.Shortcuts.ls;
import static de.endrullis.lazyseq.Shortcuts.lts;

/**
 * Option of a tuple.
 *
 * @author Stefan Endrullis (stefan@endrullis.de)
 */
public class TupleOption<T1, T2> implements Option<Tuple<T1, T2>> {
	protected final Option<Tuple<T1, T2>> underlying;

	public TupleOption(Option<Tuple<T1, T2>> underlying) {
		this.underlying = underlying;
	}

	@Override
	public @NotNull Tuple<T1, T2> get() {
		return underlying.get();
	}

	@Override
	public Tuple<T1, T2> getOrElse(Tuple<T1, T2> alternative) {
		return underlying.getOrElse(alternative);
	}

	@Override
	public Tuple<T1, T2> getOrElse(@NotNull Supplier<Tuple<T1, T2>> alternative) {
		return underlying.getOrElse(alternative);
	}

	@Override
	public @Nullable Tuple<T1, T2> getOrNull() {
		return underlying.getOrNull();
	}

	@Override
	public TupleOption<T1, T2> orElse(@Nullable Tuple<T1, T2> alternative) {
		return new TupleOption<>(underlying.orElse(alternative));
	}

	@Override
	public TupleOption<T1, T2> orElse(@NotNull Supplier<Tuple<T1, T2>> alternative) {
		return new TupleOption<>(underlying.orElse(alternative));
	}

	@Override
	public @NotNull <R> Option<R> map(@NotNull Function<Tuple<T1, T2>, R> f) {
		return underlying.map(f);
	}

	public @NotNull <R> Option<R> map(@NotNull BiFunction<T1, T2, R> f) {
		return underlying.map(t -> f.apply(t._1, t._2));
	}

	@Override
	public @NotNull <R> Option<R> flatMap(@NotNull Function<Tuple<T1, T2>, Option<R>> f) {
		return underlying.flatMap(f);
	}

	public @NotNull <R> Option<R> flatMap(@NotNull BiFunction<T1, T2, Option<R>> f) {
		return underlying.flatMap(t -> f.apply(t._1, t._2));
	}

	@Override
	public @NotNull TupleOption<T1, T2> filter(@NotNull Predicate<? super Tuple<T1, T2>> predicate) {
		return new TupleOption<>(underlying.filter(predicate));
	}

	public @NotNull TupleOption<T1, T2> filter(@NotNull BiPredicate<T1, T2> predicate) {
		return new TupleOption<>(underlying.filter(t -> predicate.test(t._1, t._2)));
	}

	@Override
	public boolean isDefined() {
		return underlying.isDefined();
	}

	@Override
	public void forEach(@NotNull Consumer<? super Tuple<T1, T2>> action) {
		underlying.forEach(action);
	}

	@Override
	public Optional<Tuple<T1, T2>> toOptional() {
		return underlying.toOptional();
	}

	@Override
	public @NotNull Iterator<Tuple<T1, T2>> iterator() {
		return underlying.iterator();
	}

	public @NotNull LazyTupleSeq<T1, T2> tupleSeqIterator() {
		return lts(ls(underlying.iterator()));
	}

}
