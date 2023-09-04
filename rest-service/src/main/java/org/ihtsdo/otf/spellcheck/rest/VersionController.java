package org.ihtsdo.otf.spellcheck.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class VersionController {
	@Autowired(required = false)
	private BuildProperties buildProperties;

	@RequestMapping(value = "/version", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BuildVersion getBuildInformation() {
		return new BuildVersion(buildProperties.getVersion(), buildProperties.getTime().toString());
	}

	public record BuildVersion(String version, String time) {
	}
}
