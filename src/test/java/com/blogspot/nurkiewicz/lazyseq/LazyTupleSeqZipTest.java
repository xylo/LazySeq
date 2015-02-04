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
	public void shouldReturnEmptySeqWhenZipNonEmptyWithEmpty() throws Exception {
		//given
		final LazySeq<Integer> empty = empty();
		final LazySeq<Integer> nonEmpty = of(1);
		final LazySeq<Integer> nonEmpty2 = of(2);

		//when
		LazyTupleSeq<Tuple<Integer, Integer>, Integer> zipped = nonEmpty.zip(empty).zip(nonEmpty2);

		//then
		assertThat(zipped).isEmpty();
	}

}
