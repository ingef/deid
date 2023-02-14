package de.ingef.deid.service.conquery;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

/**
 * Provides frontend form configurations for the conquery frontend from classpath resources.
 */
@Slf4j
@Service
public class FrontendConfigService {

	private List<JsonNode> frontendConfigs = Collections.emptyList();

	@PostConstruct
	public void init() {
		final PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());

		final Resource[] resources;
		try {
			resources = resourcePatternResolver.getResources("static/**/*.frontend.json");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		final ObjectMapper objectMapper = new ObjectMapper();
		final ObjectReader objectReader = objectMapper.readerFor(JsonObject.class);

		List<JsonNode> frontendConfigs = new ArrayList<>();
		for (Resource resource : resources) {
			try {
				final File file = resource.getFile();
				log.info("Processing frontend config: {}", file);
				final JsonNode frontendConfig = objectReader.readTree(new FileInputStream(file));
				frontendConfigs.add(frontendConfig);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		this.frontendConfigs = frontendConfigs;
	}

	public List<JsonNode> getFrontendConfigs() {
		return frontendConfigs;
	}
}
