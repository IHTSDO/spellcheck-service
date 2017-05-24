package org.ihtsdo.otf.spellcheck;

import org.ihtsdo.otf.spellcheck.service.SpellCheckService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class App {

	@Value("${dictionariesDirectory}")
	private String dictionariesDirectory;

	@Bean
	public SpellCheckService getSpellCheckService() throws IOException {
		SpellCheckService spellCheckService = new SpellCheckService();
		spellCheckService.loadDirectoryOfDictionaries(dictionariesDirectory);
		return spellCheckService;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
