package de.endrullis.lazyseq;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.function.Supplier;

import static de.endrullis.lazyseq.LazySeq.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Tomasz Nurkiewicz
 * @since 5/11/13, 3:52 PM
 */
public class LazySeqCollectTest extends AbstractBaseTestCase {

	@Mock
	private Supplier<LazySeq<Object>> supplierMock;

	@Test
	public void shouldReturnEmptySeqWhenFilteringEmptySeq() throws Exception {
		assertThat(empty().collect(Object.class)).isEqualTo(empty());
	}

	@Test
	public void shouldReturnSameSeqWhenCollectingTheSameObjects() throws Exception {
		//given
		final LazySeq<Integer> fixed = of(1, 2, 3);

		// when
		final LazySeq<Integer> collected = fixed.collect(Integer.class);

		assertThat(collected).isEqualTo(fixed);
	}

	@Test
	public void shouldReturnEmptySeqWhenNoElementsMatchInFixedSeq() throws Exception {
		//given
		final LazySeq<Object> fixed = of(-1, -2, -3);

		//when
		final LazySeq<Double> filtered = fixed.collect(Double.class);

		//then
		assertThat(filtered).isEmpty();
	}

	@Test
	public void shouldNotEvaluateTailIfHeadMatchesPredicate() throws Exception {
		//given
		final LazySeq<Object> generated = LazySeq.of("A", "BB", supplierMock);

		//when
		generated.collect(String.class);

		//then
		verifyZeroInteractions(supplierMock);
	}

	@Test
	public void shouldFilterSeveralItemsFromFiniteSeq() throws Exception {
		//given
		final LazySeq<Object> fixed = of(1, 2.2, 3, 4.4, 5);

		//when
		final LazySeq<Integer> collected = fixed.collect(Integer.class);

		//then
		assertThat(collected).containsExactly(1, 3, 5);
	}

}
