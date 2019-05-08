package de.endrullis.lazyseq;

import org.mockito.Mock;
import org.testng.annotations.Test;

import java.util.function.Supplier;

import static de.endrullis.lazyseq.LazySeq.of;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * @author Tomasz Nurkiewicz
 * @since 5/11/13, 5:40 PM
 */
public class LazySeqTakeRightTest extends AbstractBaseTestCase {

	@Mock
	private Supplier<LazySeq<Integer>> supplierMock;

	@Test
	public void shouldThrowWhenTakeWithNegativeArgument() throws Exception {
		//given
		final LazySeq<Integer> seq = of(1, 2, 3);

		try {
			//when
			seq.takeRight(-1);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException e) {
			//then
		}
	}

	@Test
	public void shouldAllowTakingNElementsFromEmptySeq() throws Exception {
		//given
		final LazySeq<Object> empty = LazySeq.empty();

		//when
		final LazySeq<Object> limited = empty.takeRight(10);

		//then
		assertThat(limited).isEmpty();
	}

	@Test
	public void shouldLeaveTooShortSeq() throws Exception {
		//given
		final LazySeq<Integer> shortSeq = of(1, 2);

		//when
		final LazySeq<Integer> limited = shortSeq.takeRight(5);

		//then
		assertThat(limited).isEqualTo(of(1, 2));
	}

	@Test
	public void shouldTrimTooLongSeq() throws Exception {
		//given
		final LazySeq<Integer> shortSeq = of(1, 2, 3, 4, 5);

		//when
		final LazySeq<Integer> limited = shortSeq.takeRight(3);

		//then
		assertThat(limited).isEqualTo(of(3, 4, 5));
	}

}
