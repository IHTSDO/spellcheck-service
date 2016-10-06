package org.ihtsdo.otf.spellcheck.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpellcheckServiceTest {

	@Autowired
	private SpellcheckService spellcheckService;

	@Test
	public void testCheckWordsReturnErrorSuggestions() throws Exception {
		final Map<String, List<String>> suggestions = spellcheckService.checkWordsReturnErrorSuggestions(Arrays.asList("app", "carot", "bean", "banana"));
		assertNotNull(suggestions);

		for (String key :suggestions.keySet()) {
			System.out.println(key);
			System.out.println(suggestions.get(key));
		}

		assertEquals(3, suggestions.size());

		assertEquals(1, suggestions.get("app").size());
		assertEquals("Correct word suggested", "apple", suggestions.get("app").get(0));

		assertEquals(1, suggestions.get("carot").size());
		assertEquals("Correct word suggested", "carrot", suggestions.get("carot").get(0));

		assertEquals("No suggestions for word not in dictionary", 0, suggestions.get("bean").size());

		assertNull("No suggestions for correctly spelled word.", suggestions.get("banana"));
	}
}
