package de.endrullis.lazyseq;

import org.testng.annotations.Test;

import static de.endrullis.lazyseq.Option.empty;
import static de.endrullis.lazyseq.Option.of;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Stefan Endrullis (stefan@endrullis.de)
 */
public class OptionFilterTest extends AbstractBaseTestCase {

	@Test
	public void shouldReturnEmptyOptionSeqWhenFilteringEmptyOption() throws Exception {
		assertThat(empty().filter(x -> true)).isEqualTo(empty());
	}

	@Test
	public void shouldReturnEmptyOptionWhenElementNotMatchingPredicate() throws Exception {
		//given
		final Option<Integer> fixed = of(1);

		//when
		final Option<Integer> filtered = fixed.filter(x -> x < 0);

		//then
		assertThat(filtered).isEmpty();
	}

	@Test
	public void shouldReturnNonEmptyOptionWhenElementMatchingPredicate() throws Exception {
		//given
		final Option<Integer> fixed = of(1);

		//when
		final Option<Integer> filtered = fixed.filter(x -> x > 0);

		//then
		assertThat(filtered).isNotEmpty();
	}

}
