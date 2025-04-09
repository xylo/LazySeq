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
	public void shouldReturnEmptyOptionWhenDoubleZippedWithEmptyOptions() throws Exception {
		//given
		final Option<Integer> empty = empty();
		final Option<Integer> nonEmpty1 = of(1);
		final Option<Integer> nonEmpty2 = of(2);

		//when
		TupleOption<Tuple<Integer, Integer>, Integer> zipped = nonEmpty1.zip(empty).zip(nonEmpty2);

		//then
		assertThat(zipped).isEmpty();
	}

	@Test
	public void shouldReturnNonEmptyOptionWhenZippingNonEmptyOptions() throws Exception {
		//given
		final Option<Integer> nonEmpty1 = of(1);
		final Option<Integer> nonEmpty2 = of(2);
		final Option<Integer> nonEmpty3 = of(3);

		//when
		TupleOption<Tuple<Integer, Integer>, Integer> zipped = nonEmpty1.zip(nonEmpty2).zip(nonEmpty3);

		//then
		assertThat(zipped).isNotEmpty();
		assertThat(zipped.get()).isEqualTo(new Tuple<>(new Tuple<>(1, 2), 3));
	}

}
