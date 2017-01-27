package de.endrullis.lazyseq;

import org.testng.annotations.Test;

import static de.endrullis.lazyseq.LazySeq.of;
import static org.fest.assertions.api.Assertions.fail;

public class LazySeqMapExTest extends AbstractBaseTestCase {

	@Test
	public void shouldThrowARuntimeExceptionWhenCalculated() {
		//given
		final LazySeq<String> strings = of("a");

		//then
		try {
			strings.mapEx(s -> Integer.parseInt(s));
			
			fail("the previous command should throw a RuntimeException");
		} catch (RuntimeException ignored) {
		}
	}

}
