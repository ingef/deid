package de.ingef.deid.service.conquery;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.base.Preconditions;
import com.nimbusds.jose.shaded.gson.JsonObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Provides frontend form configurations for the conquery frontend from classpath resources.
 */
@Slf4j
@Service
public class FrontendConfigService {

	@Autowired
	Configuration freemarkerConfig;

	private final List<JsonNode> frontendConfigs = new ArrayList<>();


	private JsonNode loadTemplateConfig(String templateName, Object dataModel) {

		try {
			final Template template = freemarkerConfig.getTemplate(templateName);
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			template.process(dataModel, new OutputStreamWriter(byteArrayOutputStream));


			final ObjectMapper objectMapper = new ObjectMapper();
			final ObjectReader objectReader = objectMapper.reader();

			return objectReader.readTree(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		}
		catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}

	}

	private JsonNode loadStaticConfig(String resourcePath) {
		Preconditions.checkNotNull(resourcePath);

		final ClassPathResource resource = new ClassPathResource(resourcePath);

		if(!resource.isFile() || !resource.isReadable()) {
			throw new IllegalStateException("Provided resource cannot be loaded: " + resourcePath);
		}

		final ObjectMapper objectMapper = new ObjectMapper();
		final ObjectReader objectReader = objectMapper.readerFor(JsonObject.class);

		try {
			final File file = resource.getFile();
			log.info("Processing frontend config: {}", file);
			return objectReader.readTree(new FileInputStream(file));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void registerTemplateConfig(String templateName, Object dataModel) {
		frontendConfigs.add(loadTemplateConfig(templateName, dataModel));
	}

	public void registerStaticConfig(String resourcePath) {
		frontendConfigs.add(loadStaticConfig(resourcePath));
	}

	public List<JsonNode> getFrontendConfigs() {
		return frontendConfigs;
	}
}
