package de.endrullis.lazyseq;

import org.testng.annotations.Test;

import static de.endrullis.lazyseq.LazySeq.*;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Tomasz Nurkiewicz
 * @since 5/12/13, 8:37 AM
 */
public class LazySeqMinMaxTest extends AbstractBaseTestCase {

	@Test
	public void shouldReturnEmptyOptionOnEmptySeq() throws Exception {
		//given
		final LazySeq<Integer> empty = empty();

		//when
		final Option<Integer> min = empty.min((a, b) -> a - b);
		final Option<Integer> max = empty.max((a, b) -> a - b);

		//then
		assertThat(min).isEqualTo(Option.<Integer>empty());
		assertThat(max).isEqualTo(Option.<Integer>empty());
	}

	@Test
	public void shouldReturnEmptyOptionOnEmptySeqByProperty() throws Exception {
		//given
		final LazySeq<Integer> empty = empty();

		//when
		final Option<Integer> min = empty.minBy(Math::abs);
		final Option<Integer> max = empty.maxBy(Math::abs);

		//then
		assertThat(min).isEqualTo(Option.<Integer>empty());
		assertThat(max).isEqualTo(Option.<Integer>empty());
	}

	@Test
	public void shouldReturnThisElementWhenSingleElementSeq() throws Exception {
		//given
		final LazySeq<Integer> single = of(1);

		//when
		final Option<Integer> min = single.min((a, b) -> a - b);
		final Option<Integer> max = single.max((a, b) -> a - b);

		//then
		assertThat(min).isEqualTo(Option.of(1));
		assertThat(max).isEqualTo(Option.of(1));
	}

	@Test
	public void shouldReturnThisElementWhenSingleElementSeqByProperty() throws Exception {
		//given
		final LazySeq<Integer> single = of(1);

		//when
		final Option<Integer> min = single.minBy(Math::abs);
		final Option<Integer> max = single.maxBy(Math::abs);

		//then
		assertThat(min).isEqualTo(Option.of(1));
		assertThat(max).isEqualTo(Option.of(1));
	}

	@Test
	public void shouldFindBiggestAndSmallestValueInSeqOfIntegers() throws Exception {
		//given
		final LazySeq<Integer> fixed = of(3, -2, 8, 5, -4, 11, 2, 1);

		//when
		final Option<Integer> min = fixed.min((a, b) -> a - b);
		final Option<Integer> max = fixed.max((a, b) -> a - b);

		//then
		assertThat(min).isEqualTo(Option.of(-4));
		assertThat(max).isEqualTo(Option.of(11));
	}

	@Test
	public void shouldFindBiggestAndSmallestValueInSeqOfIntegersByProperty() throws Exception {
		//given
		final LazySeq<Integer> fixed = of(3, -2, 8, 5, -4, 11, 2, 1);

		//when
		final Option<Integer> min = fixed.minBy(Math::abs);
		final Option<Integer> max = fixed.maxBy(Math::abs);

		//then
		assertThat(min).isEqualTo(Option.of(1));
		assertThat(max).isEqualTo(Option.of(11));
	}

	@Test
	public void shouldFindBiggestAndSmallestValueInLazyButFiniteSeq() throws Exception {
		//given
		final LazySeq<Integer> lazy = cons(3,
				() -> cons(-2,
						() -> cons(8,
								() -> cons(5,
										() -> cons(-4,
												() -> cons(11,
														() -> cons(2,
																() -> of(1))))))));

		//when
		final Option<Integer> min = lazy.min((a, b) -> a - b);
		final Option<Integer> max = lazy.max((a, b) -> a - b);

		//then
		assertThat(min).isEqualTo(Option.of(-4));
		assertThat(max).isEqualTo(Option.of(11));
	}

	@Test
	public void shouldFindBiggestAndSmallestValueInLazyButFiniteSeqByProperty() throws Exception {
		//given
		final LazySeq<Integer> lazy = cons(3,
				() -> cons(-2,
						() -> cons(8,
								() -> cons(5,
										() -> cons(-4,
												() -> cons(11,
														() -> cons(2,
																() -> of(1))))))));

		//when
		final Option<Integer> min = lazy.minBy(Math::abs);
		final Option<Integer> max = lazy.maxBy(Math::abs);

		//then
		assertThat(min).isEqualTo(Option.of(1));
		assertThat(max).isEqualTo(Option.of(11));
	}

	@Test
	public void shouldFindShortestAndLongestStrings() throws Exception {
		//given
		final LazySeq<String> single = of(loremIpsum());

		//when
		final Option<String> min = single.min((a, b) -> a.length() - b.length());
		final Option<String> max = single.max((a, b) -> a.length() - b.length());

		//then
		assertThat(min).isEqualTo(Option.of("id"));
		assertThat(max).isEqualTo(Option.of("consectetur"));
	}

	@Test
	public void shouldFindShortestAndLongestStringsByLengthProperty() throws Exception {
		//given
		final LazySeq<String> single = of(loremIpsum());

		//when
		final Option<String> min = single.minBy(String::length);
		final Option<String> max = single.maxBy(String::length);

		//then
		assertThat(min).isEqualTo(Option.of("id"));
		assertThat(max).isEqualTo(Option.of("consectetur"));
	}

	@Test
	public void shouldFindFirstAndLastStringAlphabetically() throws Exception {
		//given
		final LazySeq<String> single = of(loremIpsum());

		//when
		final Option<String> min = single.min(String::compareTo);
		final Option<String> max = single.max(String::compareTo);

		//then
		assertThat(min).isEqualTo(Option.of("adipiscing"));
		assertThat(max).isEqualTo(Option.of("sit"));
	}

	@Test
	public void shouldFindFirstAndLastStringByLastCharacter() throws Exception {
		//given
		final LazySeq<String> single = of(loremIpsum());

		//when
		final Option<String> min = single.minBy(s -> s.charAt(s.length() - 1));
		final Option<String> max = single.maxBy(s -> s.charAt(s.length() - 1));

		//then
		assertThat(min).isEqualTo(Option.of("ligula"));
		assertThat(max).isEqualTo(Option.of("sit"));
	}

	private String[] loremIpsum() {
		return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi id metus at ligula convallis imperdiet. ".toLowerCase().split("[ \\.,]+");
	}

}
