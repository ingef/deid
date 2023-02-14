package de.ingef.deid.controller;


import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import de.ingef.deid.service.conquery.FrontendConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("conquery/frontend")
public class ConqueryFrontendConfigController {

	@Autowired
	private FrontendConfigService frontendConfigService;

	@GetMapping
	public List<JsonNode> getFrontendConfigs() {
		return frontendConfigService.getFrontendConfigs();
	}
}
