package de.ingef.deid;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import de.ingef.deid.service.conquery.FrontendConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(args = {"--dev"})
public class FrontendConfigServiceTest {

	@Autowired
	private FrontendConfigService frontendConfigService;

	@Test
	public void getConfigs() {

		final List<JsonNode> frontendConfigs = frontendConfigService.getFrontendConfigs();

		assertThat(frontendConfigs).hasSize(2);
	}
}
