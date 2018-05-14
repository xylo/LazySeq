package de.endrullis.lazyseq;

import de.endrullis.lazyseq.samples.Seqs;
import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.function.Supplier;

import static de.endrullis.lazyseq.LazySeq.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Tomasz Nurkiewicz
 * @since 5/11/13, 6:07 PM
 */
public class LazySeqDropRightTest extends AbstractBaseTestCase {

	@Mock
	private Supplier<LazySeq<Integer>> supplierMock;

	@Test
	public void shouldThrowWhenNegativeNumberIndexOnEmptySeq() throws Exception {
		//given
		final LazySeq<Object> empty = empty();

		try {
			//when
			long numberOfItemsToDropAtEnd = -1;
			empty.dropRight(numberOfItemsToDropAtEnd);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException e) {
			//then
		}
	}

	@Test
	public void shouldThrowWhenNegativeNumberIndexOnNonEmptySeq() throws Exception {
		//given
		final LazySeq<Integer> nonEmpty = of(1, 2, 3);

		try {
			//when
			long numberOfItemsToDropAtEnd = -1;
			nonEmpty.dropRight(numberOfItemsToDropAtEnd);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException e) {
			//then
		}
	}

	@Test
	public void shouldThrowWhenNegativeNumberIndexOnInfiniteSeq() throws Exception {
		//given
		final LazySeq<Integer> infinite = Seqs.primes();

		try {
			//when
			long numberOfItemsToDropAtEnd = -1;
			infinite.dropRight(numberOfItemsToDropAtEnd);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException e) {
			//then
		}
	}

	@Test
	public void shouldReturnEmptySeqWhenNotDroppingElementsFromEmptySeq() throws Exception {
		assertThat(empty().dropRight((long) 0)).isEmpty();
	}

	@Test
	public void shouldReturnEmptySeqWhenDroppingElementsFromEmptySeq() throws Exception {
		assertThat(empty().dropRight((long) 5)).isEmpty();
	}

	@Test
	public void shouldReturnEmptySeqWhenSubstreamPastEnd() throws Exception {
		assertThat(of(1, 2).dropRight((long) 2)).isEmpty();
	}

	@Test
	public void shouldReturnSubstreamWithoutPrefix() throws Exception {
		assertThat(of(1, 2, 3, 4, 5).dropRight((long) 2)).isEqualTo(of(1, 2, 3));
	}

}
