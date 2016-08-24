package de.endrullis.lazyseq;

import org.testng.annotations.Test;

import java.util.Map;

import static de.endrullis.lazyseq.LazySeq.of;
import static de.endrullis.lazyseq.Shortcuts.jMap;
import static de.endrullis.lazyseq.Shortcuts.t;
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
