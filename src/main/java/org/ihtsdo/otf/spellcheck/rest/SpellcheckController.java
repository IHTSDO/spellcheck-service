package org.ihtsdo.otf.spellcheck.rest;

import org.ihtsdo.otf.spellcheck.service.SpellcheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class SpellcheckController {

	@Autowired
	private SpellcheckService spellcheckService;

	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.GET, produces = "application/json")
	public Map<String, List<String>> checkWords(@RequestParam Set<String> words) {
		return spellcheckService.checkWordsReturnErrorSuggestions(words);
	}

}
