package de.ingef.deid.service;

import java.util.Optional;

import de.ingef.deid.model.Job;
import de.ingef.deid.model.JobState;
import de.ingef.deid.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReviewService {

	@Autowired
	private JobRepository jobRepository;

	@EventListener
	public void startReviewProcess(DeidentificationCompleteEvent event) {

		final Optional<Job> byId = jobRepository.findById(event.getJobId());

		final Job job = byId.orElseThrow();

		log.info("Deidentification completed successfully for job '{}'", job.getId());


		// TODO not actually finished here, implement review
		job.setStatus(JobState.FINISHED);

		jobRepository.save(job);
	}
}
