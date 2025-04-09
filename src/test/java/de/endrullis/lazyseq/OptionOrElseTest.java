package de.endrullis.lazyseq;

import org.testng.annotations.Test;

import static de.endrullis.lazyseq.Option.empty;
import static de.endrullis.lazyseq.Option.of;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Stefan Endrullis (stefan@endrullis.de)
 */
public class OptionOrElseTest extends AbstractBaseTestCase {

	@Test
	public void shouldReturnNonEmptyOptionIfOptionIsEmpty() throws Exception {
		//given
		final Option<Integer> fixed = empty();

		//when
		final Option<Integer> elem = fixed.orElse(2);

		//then
		assertThat(elem).isNotEmpty();
		assertThat(elem.get()).isEqualTo(2);
	}

	@Test
	public void shouldReturnNonEmptyOptionIfOptionIsEmpty_supplied() throws Exception {
		//given
		final Option<Integer> fixed = empty();

		//when
		final Option<Integer> elem = fixed.orElse(() -> 2);

		//then
		assertThat(elem).isNotEmpty();
		assertThat(elem.get()).isEqualTo(2);
	}

	@Test
	public void shouldReturnNonEmptyOptionIfOptionIsNonEmpty() throws Exception {
		//given
		final Option<Integer> fixed = of(1);

		//when
		final Option<Integer> elem = fixed.orElse(2);

		//then
		assertThat(elem).isNotEmpty();
		assertThat(elem.get()).isEqualTo(1);
	}

	@Test
	public void shouldReturnNonEmptyOptionIfOptionIsNonEmpty_supplied() throws Exception {
		//given
		final Option<Integer> fixed = of(1);

		//when
		final Option<Integer> elem = fixed.orElse(() -> 2);

		//then
		assertThat(elem).isNotEmpty();
		assertThat(elem.get()).isEqualTo(1);
	}

}
