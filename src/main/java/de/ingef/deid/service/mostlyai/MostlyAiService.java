package de.ingef.deid.service.mostlyai;

import java.util.Optional;

import de.ingef.deid.model.Job;
import de.ingef.deid.model.JobState;
import de.ingef.deid.repository.JobRepository;
import de.ingef.deid.service.DeidentificationCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MostlyAiService {


	@Autowired
	ApplicationEventPublisher publisher;

	@Autowired
	private JobRepository jobRepository;

	@EventListener
	public void handleDeidentificationEvent(MostlyAISythetizationStartEvent event) {
		final Optional<Job> byId = jobRepository.findById(event.getJobId());
		if (byId.isEmpty()) {
			throw new IllegalStateException("Cannot find job '" + event.getJobId() + "'");
		}
		final Job job = byId.get();
		log.info("Received job '{}' for mostly-ai synthetization: {}", event.getJobId(), job.getName());

		job.setStatus(JobState.RUNNING);
		jobRepository.save(job);

		// TODO implement synthetization

		publisher.publishEvent(new DeidentificationCompleteEvent(event.getJobId()));
	}
}
