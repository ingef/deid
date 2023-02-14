package de.ingef.deid;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import de.ingef.deid.service.conquery.FrontendConfigService;
import org.junit.jupiter.api.Test;

public class FrontendConfigServiceTest {

	@Test
	public void getConfigs() {
		final FrontendConfigService frontendConfigService = new FrontendConfigService();
		frontendConfigService.init();

		final List<JsonNode> frontendConfigs = frontendConfigService.getFrontendConfigs();

		assertThat(frontendConfigs).isNotEmpty();
	}
}
