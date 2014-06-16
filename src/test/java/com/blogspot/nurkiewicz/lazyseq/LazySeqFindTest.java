package com.blogspot.nurkiewicz.lazyseq;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static com.blogspot.nurkiewicz.lazyseq.LazySeq.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Tomasz Nurkiewicz
 * @author Stefan Endrullis
 */
public class LazySeqFindTest extends AbstractBaseTestCase {

	@Mock
	private Supplier<LazySeq<String>> supplierMock;

	@Test
	public void shouldReturnEmptyResultWhenFindingSearchingInEmptySeq() throws Exception {
		assertThat(empty().find(x -> false).isPresent()).isFalse();
	}

	@Test
	public void shouldReturnEmptyResultWhenNoElementsMatchInFixedSeq() throws Exception {
		//given
		final LazySeq<Integer> fixed = of(-1, -2, -3);

		//when
		final Optional<Integer> found = fixed.find(x -> x > 0);

		//then
		assertThat(found.isPresent()).isFalse();
	}

	@Test
	public void shouldReturnEmptyResultWhenNoElementsMatchInFiniteSeq() throws Exception {
		//given
		final LazySeq<Integer> fixed = LazySeq.<Integer>of(-1, -2, () -> of(-3, -4));

		//when
		final Optional<Integer> found = fixed.find(x -> x > 0);

		//then
		assertThat(found.isPresent()).isFalse();
	}

	@Test
	public void shouldNotEvaluateTailIfHeadMatchesPredicate() throws Exception {
		//given
		final LazySeq<String> generated = LazySeq.of("A", "BB", supplierMock);

		//when
		generated.find(s -> !s.isEmpty());

		//then
		verifyZeroInteractions(supplierMock);
	}

	@Test
	public void shouldEvaluateTailOnceWhenFirstElementNotMatching() throws Exception {
		//given
		final LazySeq<String> generated = LazySeq.cons("", supplierMock);
		given(supplierMock.get()).willReturn(of("C"));

		//when
		generated.find(s -> !s.isEmpty());

		//then
		Mockito.verify(supplierMock).get();
	}

	@Test
	public void shouldEvaluateTailMultipleTimesToReturnLastElement() throws Exception {
		//given
		final LazySeq<String> generated = LazySeq.<String>cons("a", () -> cons("bb", () -> cons("ccc", LazySeq::<String>empty)));

		//when
		final Optional<String> found = generated.find(s -> s.length() >= 3);

		//then
		assertThat(found).isEqualTo(Optional.of("ccc"));
	}

}
