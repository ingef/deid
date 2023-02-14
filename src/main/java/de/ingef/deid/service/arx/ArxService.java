package de.ingef.deid.service.arx;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import de.ingef.deid.model.Job;
import de.ingef.deid.repository.JobRepository;
import de.ingef.deid.service.DeidentificationCompleteEvent;
import de.ingef.deid.service.conquery.FrontendConfigService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ArxService {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private FrontendConfigService frontendConfigService;

	/**
	 * Available privacy models.
	 */
	private final TreeMap<String, PrivacyModel> privacyModels = new TreeMap<>(Map.of(
			"K_ANONYMITY_5", KAnonymity.builder()
									   .localizedLabels(
											   Map.of(
													   "de", "K-Anonymität 5",
													   "en", "K-Anonymity 5"
											   ))
									   .k(5).build(),
			"K_ANONYMITY_11", KAnonymity.builder()
										.localizedLabels(
												Map.of(
														"de", "K-Anonymität 11",
														"en", "K-Anonymity 11"
												))
										.k(11).build(),
			"PITMAN_1_PERCENT", PopulationUniqueness.builder()
													.localizedLabels(Map.of(
															"de", "Pitman 1%",
															"en", "Pitman 1%"
													))
													.populationUniquenessModel(RiskModelPopulationUniqueness.PopulationUniquenessModel.PITMAN)
													.build()
	));

	@PostConstruct
	public void init() {
		frontendConfigService.registerTemplateConfig("conquery/arx_form.frontend_conf.json.ftl", Map.of("privacyModels",privacyModels));
	}

	@EventListener
	public void handleDeidentificationEvent(ArxDeidentificationStartEvent event) {
		final Optional<Job> byId = jobRepository.findById(event.getJobId());
		if (byId.isEmpty()) {
			throw new IllegalStateException("Cannot find job '" + event.getJobId() + "'");
		}
		log.info("Received job '{}' for arx anonymization: {}", event.getJobId(), byId.get().getName());

		publisher.publishEvent(new DeidentificationCompleteEvent(event.getJobId()));
	}
}
