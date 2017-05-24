package org.ihtsdo.otf.spellcheck.rest;

import org.ihtsdo.otf.spellcheck.service.SpellCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class SpellCheckController {

	@Autowired
	private SpellCheckService spellCheckService;

	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.GET, produces = "application/json")
	public Map<String, List<String>> checkWords(@RequestParam Set<String> words) {
		return spellCheckService.checkWordsReturnErrorSuggestions(words);
	}

}
