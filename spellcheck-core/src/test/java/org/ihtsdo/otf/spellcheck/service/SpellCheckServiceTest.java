package org.ihtsdo.otf.spellcheck.service;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SpellCheckServiceTest {

	private SpellCheckService spellCheckService;

	@Before
	public void setup() throws IOException {
		spellCheckService = new SpellCheckService();
		spellCheckService.loadDirectoryOfDictionaries("src/test/resources/dictionaries");
	}

	@Test
	public void testCheckWordsReturnErrorSuggestions() throws Exception {
		final Map<String, List<String>> suggestions = spellCheckService.checkWordsReturnErrorSuggestions(
				Arrays.asList("app", "carot", "bean", "banana"));
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

	@Test
	public void testEnglishStopWord() {
		Map<String, List<String>> suggestions = checkWord("the");
		assertNotNull(suggestions);
		assertEquals(0, suggestions.size());
	}

	@Test
	public void testIgnoreWordShorterThanMin() {
		Map<String, List<String>> suggestions = checkWord("of");
		assertNotNull(suggestions);
		assertEquals(0, suggestions.size());
	}

	private Map<String, List<String>> checkWord(String word) {
		return spellCheckService.checkWordsReturnErrorSuggestions(Collections.singleton(word));
	}
}
