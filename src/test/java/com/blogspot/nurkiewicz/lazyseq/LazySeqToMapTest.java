package com.blogspot.nurkiewicz.lazyseq;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.blogspot.nurkiewicz.lazyseq.LazySeq.of;
import static com.blogspot.nurkiewicz.lazyseq.Shortcuts.*;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Stefan Endrullis (endrullis@iat.uni-leipzig.de)
 */
public class LazySeqToMapTest extends AbstractBaseTestCase {

	@Test
	public void testToMapWithKeyAndValueFunction() throws Exception {
		//given
		final LazySeq<String> strings = of("a", "aa", "b");

		//when
		Map<String, Integer> map = strings.toMap(s -> s, s -> s.length());

		//then
		assertThat(map).isEqualTo(jMap(t("a", 1), t("aa", 2), t("b", 1)));
	}

}