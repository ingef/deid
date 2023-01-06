package de.ingef.deid.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.ImmutableList;
import de.ingef.deid.model.Job;
import de.ingef.deid.repository.JobRepository;
import de.ingef.deid.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@ControllerAdvice
@RequestMapping("job")
public class JobController {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private JobService jobService;

	@GetMapping()
	public ResponseEntity<List<Job>> getJobsList() {
		return new ResponseEntity<>(ImmutableList.copyOf(jobRepository.findAll()), HttpStatus.OK);
	}

	@PostMapping()
	public Job createJob(@RequestBody() Job job) {

		return jobService.setupJob(job);

	}

	@GetMapping("{jobId}")
	public Job getJobById(@PathVariable UUID jobId){
		final Optional<Job> byId = jobRepository.findById(jobId);

		if (byId.isPresent()) {
			return byId.get();
		}

		throw new NoSuchElementException(String.format("Cannot find job with id '%s'", jobId));
	}
}
