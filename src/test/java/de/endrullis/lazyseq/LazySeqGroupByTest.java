package de.endrullis.lazyseq;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.endrullis.lazyseq.LazySeq.of;
import static de.endrullis.lazyseq.Shortcuts.*;
import static java.util.stream.Collectors.toSet;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Stefan Endrullis (endrullis@iat.uni-leipzig.de)
 */
public class LazySeqGroupByTest extends AbstractBaseTestCase {

	@Test
	public void testGroupByWithKeyFunction() throws Exception {
		//given
		final LazySeq<String> strings = of("a", "aa", "aaa", "b", "bb", "c");

		//when
		Map<Integer, List<String>> map = strings.groupBy(s -> s.length()).toMap();

		//then
		assertThat(map).isEqualTo(jMap(t(1, jList("a", "b", "c")), t(2, jList("aa", "bb")), t(3, jList("aaa"))));
	}

	@Test
	public void testGroupByWithKeyAndValueFunction() throws Exception {
		//given
		final LazySeq<String> strings = of("a", "aa", "aaa", "b", "bb", "c");

		//when
		Map<Integer, List<Integer>> map = strings.groupBy(s -> s.length(), s -> s.length()).toMap();

		//then
		assertThat(map).isEqualTo(jMap(t(1, jList(1, 1, 1)), t(2, jList(2, 2)), t(3, jList(3))));
	}

	@Test
	public void testGroupByWithKeyAndValueAndValueCollectorFunction() throws Exception {
		//given
		final LazySeq<String> strings = of("a", "aa", "aaa", "b", "bb", "c");

		//when
		Map<Integer, Set<String>> map = strings.groupBy(s -> s.length(), s -> s, toSet()).toMap();

		//then
		assertThat(map).isEqualTo(jMap(t(1, jSet("a", "b", "c")), t(2, jSet("aa", "bb")), t(3, jSet("aaa"))));
	}

	@Test
	public void testGroupByWithKeyAndValueCollectorFunction() throws Exception {
		//given
		final LazySeq<String> strings = of("a", "aa", "aaa", "b", "bb", "c");

		//when
		Map<Integer, Set<String>> map = strings.groupBy(s -> s.length(), toSet()).toMap();

		//then
		assertThat(map).isEqualTo(jMap(t(1, jSet("a", "b", "c")), t(2, jSet("aa", "bb")), t(3, jSet("aaa"))));
	}

	@Test
	public void testGroupByWithMapValues() throws Exception {
		//given
		final LazySeq<String> strings = of("a", "aa", "aaa", "b", "bb", "c");

		//when
		Map<Integer, String> map = strings.groupBy(s -> s.length()).mapValues(vs -> vs.get(0)).toMap();

		//then
		assertThat(map).isEqualTo(jMap(t(1, "a"), t(2, "aa"), t(3, "aaa")));
	}

	@Test
	public void testGroupByWithMapKeys() throws Exception {
		//given
		final LazySeq<String> strings = of("a", "aa", "aaa", "b", "bb", "c");

		//when
		Map<Integer, List<String>> map = strings.groupBy(s -> s.length()).mapKeys(k -> k + 1).toMap();

		//then
		assertThat(map).isEqualTo(jMap(t(2, jList("a", "b", "c")), t(3, jList("aa", "bb")), t(4, jList("aaa"))));
	}

}
