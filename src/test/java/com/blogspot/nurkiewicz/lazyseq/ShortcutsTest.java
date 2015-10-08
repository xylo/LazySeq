package com.blogspot.nurkiewicz.lazyseq;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.blogspot.nurkiewicz.lazyseq.Shortcuts.jMap;
import static com.blogspot.nurkiewicz.lazyseq.Shortcuts.t;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Stefan Endrullis (endrullis@iat.uni-leipzig.de)
 */
public class ShortcutsTest {

	@Test
	public void testJavaMapCreationViaJMap() throws Exception {
		// given
		Map<Integer, String> map1 = jMap(t(1, "a"), t(2, "b"));
		Map<Integer, String> map2 = new HashMap<Integer, String>() {{
			put(1, "a");
			put(2, "b");
		}};

		//then
		assertThat(map1).isEqualTo(map2);
	}

}
