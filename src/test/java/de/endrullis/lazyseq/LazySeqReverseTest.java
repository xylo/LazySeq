package de.endrullis.lazyseq;

import org.testng.annotations.Test;

import java.util.Arrays;

import static de.endrullis.lazyseq.LazySeq.of;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Tomasz Nurkiewicz
 * @since 5/11/13, 7:49 PM
 */
public class LazySeqReverseTest extends AbstractBaseTestCase {

	@Test
	public void shouldReturnEmptyList() throws Exception {
		//given
		final LazySeq<Integer> empty = LazySeq.empty();

		//when
		final LazySeq<Integer> result = empty.reverse();

		//then
		assertThat(result.isEmpty()).isTrue();
	}

	@Test
	public void shouldReturnSumOfNumbersInFixedSeqStartingFromIdentity() throws Exception {
		//given
		final LazySeq<Integer> fixed = of(1, 2, 4, 7);

		//when
		final LazySeq<Integer> result = fixed.reverse();

		//then
		assertThat(result.toList()).isEqualTo(Arrays.asList(7, 4, 2, 1));
	}

}
