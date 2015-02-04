package com.blogspot.nurkiewicz.lazyseq;

import org.mockito.Mock;
import org.testng.annotations.Test;

import static com.blogspot.nurkiewicz.lazyseq.LazySeq.*;
import static com.blogspot.nurkiewicz.lazyseq.samples.Seqs.primes;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;


/**
 * @author Tomasz Nurkiewicz
 * @since 5/12/13, 10:28 AM
 */
public class LazyTupleSeqZipTest extends AbstractBaseTestCase {

	@Test
	public void shouldReturnEmptySeqWhenDoubleZippedWithEmptySeq() throws Exception {
		//given
		final LazySeq<Integer> empty = empty();
		final LazySeq<Integer> nonEmpty1 = of(1);
		final LazySeq<Integer> nonEmpty2 = of(2);

		//when
		LazyTupleSeq<Tuple<Integer, Integer>, Integer> zipped = nonEmpty1.zip(empty).zip(nonEmpty2);

		//then
		assertThat(zipped).isEmpty();
	}

	@Test
	public void shouldReturnNonEmptySeqWhenZippingNonEmptySeqs() throws Exception {
		//given
		final LazySeq<Integer> nonEmpty1 = of(1);
		final LazySeq<Integer> nonEmpty2 = of(2);
		final LazySeq<Integer> nonEmpty3 = of(3);

		//when
		LazyTupleSeq<Tuple<Integer, Integer>, Integer> zipped = nonEmpty2.zip(nonEmpty1).zip(nonEmpty3);

		//then
		assertThat(zipped).isNotEmpty();
	}

}
