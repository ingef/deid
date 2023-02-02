package de.ingef.deid.service;

import de.ingef.deid.model.Job;
import de.ingef.deid.model.JobState;
import de.ingef.deid.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Service for handling incoming job related requests: Creation, Listing, Status.
 */
@Service
@Slf4j
public class JobService {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	public Job setupJob(Job job) {
		job.setStatus(JobState.NEW);
		final Job savedJob = jobRepository.save(job);

		publisher.publishEvent(savedJob.getDeidentificationMethod().createEvent());

		return savedJob;
	}

}
