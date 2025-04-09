package de.endrullis.lazyseq;

import org.testng.annotations.Test;

import static de.endrullis.lazyseq.Option.*;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Stefan Endrullis (stefan@endrullis.de)
 */
public class OptionEnabledTest extends AbstractBaseTestCase {

	@Test
	public void shouldReturnEmptyOptionWhenElementIsNull() throws Exception {
		//given
		final Option<Object> option = of(null);

		//then
		assertThat(option).isEqualTo(empty());
		assertThat(option).isEqualTo(none());
		assertThat(option.isEmpty()).isTrue();
		assertThat(option.isDefined()).isFalse();
	}

	@Test
	public void shouldReturnNonEmptyOptionWhenElementIsNotNull() throws Exception {
		//given
		final Option<Integer> option = of(1);
		
		//then
		assertThat(option).isNotEqualTo(empty());
		assertThat(option).isNotEqualTo(none());
		assertThat(option.isEmpty()).isFalse();
		assertThat(option.isDefined()).isTrue();
	}

}
