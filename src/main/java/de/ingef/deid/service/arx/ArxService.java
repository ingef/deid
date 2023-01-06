package de.ingef.deid.service.arx;

import java.util.Optional;

import de.ingef.deid.model.Job;
import de.ingef.deid.repository.JobRepository;
import de.ingef.deid.service.DeidentificationCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArxService {

	@Autowired
	ApplicationEventPublisher publisher;

	@Autowired
	private JobRepository jobRepository;

	@EventListener
	public void handleDeidentificationEvent(ArxDeidentificationEvent event) {
		final Optional<Job> byId = jobRepository.findById(event.getJobId());
		if (byId.isEmpty()) {
			throw new IllegalStateException("Cannot find job '" + event.getJobId() + "'");
		}
		log.info("Received job '{}' for arx anonymization: {}", event.getJobId(), byId.get().getName());

		publisher.publishEvent(new DeidentificationCompleteEvent(event.getJobId()));
	}
}
