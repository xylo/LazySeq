package de.endrullis.lazyseq;

import org.testng.annotations.Test;

import static de.endrullis.lazyseq.Option.empty;
import static de.endrullis.lazyseq.Option.of;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Stefan Endrullis (stefan@endrullis.de)
 */
public class OptionGetTest extends AbstractBaseTestCase {

	@Test
	public void shouldReturn2IfOptionIsEmpty() throws Exception {
		//given
		final Option<Integer> fixed = empty();

		//when
		final Integer elem = fixed.getOrElse(2);

		//then
		assertThat(elem).isEqualTo(2);
	}

	@Test
	public void shouldReturn2IfOptionIsEmpty_supplied() throws Exception {
		//given
		final Option<Integer> fixed = empty();

		//when
		final Integer elem = fixed.getOrElse(() -> 2);

		//then
		assertThat(elem).isEqualTo(2);
	}

	@Test
	public void shouldReturnNullIfOptionIsEmpty() throws Exception {
		//given
		final Option<Integer> fixed = empty();

		//when
		final Integer elem = fixed.getOrNull();

		//then
		assertThat(elem).isNull();
	}


	@Test
	public void shouldReturn1IfOptionIs1() throws Exception {
		//given
		final Option<Integer> fixed = of(1);

		//when
		final Integer elem = fixed.getOrElse(2);

		//then
		assertThat(elem).isEqualTo(1);
	}

	@Test
	public void shouldReturn1IfOptionIs1_supplied() throws Exception {
		//given
		final Option<Integer> fixed = of(1);

		//when
		final Integer elem = fixed.getOrElse(() -> 2);

		//then
		assertThat(elem).isEqualTo(1);
	}

	@Test
	public void shouldReturnNotNullIfOptionIsNonEmpty() throws Exception {
		//given
		final Option<Integer> fixed = of(1);

		//when
		final Integer elem = fixed.getOrNull();

		//then
		assertThat(elem).isNotNull();
	}

}
