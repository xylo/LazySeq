package com.blogspot.nurkiewicz.lazyseq;

import org.testng.annotations.Test;

import static com.blogspot.nurkiewicz.lazyseq.Shortcuts.t;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Tests for {@link Tuple}
 *
 * @author Stefan Endrullis (endrullis@iat.uni-leipzig.de)
 */
public class TupleTest extends AbstractBaseTestCase {

	@Test
	public void shouldReturnCorrectFieldValues() throws Exception {
		//given
		Tuple<String, Integer> tuple = t("foo", 2);

		//then
		assertThat(tuple._1).isEqualTo("foo");
		assertThat(tuple._2).isEqualTo(2);
	}

	@Test
	public void shouldReturnTheSameHashCodeIfTupleContentsAreEqual() throws Exception {
		//given
		Tuple<String, Integer> tuple  = t("foo", 2);
		Tuple<String, Integer> tuple_ = t("foo", 2);

		//then
		assertThat(tuple.hashCode()).isEqualTo(tuple_.hashCode());
	}

	@Test
	public void shouldBeEqualIfTupleContentsAreEqual() throws Exception {
		//given
		Tuple<String, Integer> tuple    = t("foo", 2);
		Tuple<String, Integer> tuple_   = t("foo", 2);
		Tuple<String, Integer> tuple1m  = t("bar", 2);
		Tuple<String, Integer> tuple2m  = t("foo", 3);
		Tuple<String, Integer> tuple12m = t("bar", 3);

		//then
		assertThat(tuple).isEqualTo(tuple_);
		assertThat(tuple).isNotEqualTo(tuple1m);
		assertThat(tuple).isNotEqualTo(tuple2m);
		assertThat(tuple).isNotEqualTo(tuple12m);
	}

	@Test
	public void shouldReturnAReadableStringRepresentation() throws Exception {
		//given
		Tuple<String, Integer> tuple    = t("foo", 2);

		//then
		assertThat(tuple.toString()).isEqualTo("(foo,2)");
	}

}
