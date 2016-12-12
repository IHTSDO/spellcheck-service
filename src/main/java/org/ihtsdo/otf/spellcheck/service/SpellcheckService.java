package org.ihtsdo.otf.spellcheck.service;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SpellcheckService {

	private SpellChecker spellChecker;

	private static final String CHECK_ERROR = "Problem checking and suggesting words with spell checker.";
	private static final int MIN_WORD_LENGTH = 3; // The Lucene SpellChecker does not deal with words shorter than this

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public SpellcheckService(String dictionariesDirectoryPath) throws IOException {
		spellChecker = new SpellChecker(new RAMDirectory());
		File dictionariesDir = new File(dictionariesDirectoryPath);
		if (!dictionariesDir.isDirectory()) {
			logger.error("Specified dictionaries directory is not a directory {}", dictionariesDir.getAbsolutePath());
			return;
		}

		long dictionariesLoaded = 0;
		final File[] files = dictionariesDir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".txt")) {
					logger.info("Loading dictionary {}.", file.getName());
					spellChecker.indexDictionary(new PlainTextDictionary(new FileReader(file)), new IndexWriterConfig(new StandardAnalyzer()), true);
					dictionariesLoaded++;
				}
			}
		}

		if (dictionariesLoaded > 0) {
			logger.info("{} dictionaries loaded.", dictionariesLoaded);
		} else {
			logger.error("No dictionaries loaded.");
		}
	}

	public Map<String, List<String>> checkWordsReturnErrorSuggestions(Collection<String> words) {
		Map<String, List<String>> errorSuggestions = new HashMap<>();
		try {
			for (String word : words) {
				if (word.length() >= MIN_WORD_LENGTH && !spellChecker.exist(word)) {
					errorSuggestions.put(word, Arrays.asList(spellChecker.suggestSimilar(word, 5)));
				}
			}
		} catch (IOException e) {
			logger.error(CHECK_ERROR, e);
			throw new InternalError(CHECK_ERROR);
		}
		return errorSuggestions;
	}

}
