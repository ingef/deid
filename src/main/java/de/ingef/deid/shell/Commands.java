package de.ingef.deid.shell;

import de.ingef.deid.model.Job;
import de.ingef.deid.repository.JobRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Commands {

	@PostConstruct
	public void init() {
		if(loadDemoData) {
			fillDemoData();
		}
	}

	@Value("${loadDemoData:false}")
	private boolean loadDemoData;

	@Autowired
	private JobRepository jobRepository;

	private void fillDemoData() {
		log.info("Adding test jobs to repository");
		jobRepository.save(new Job("Anonymization A"));
		jobRepository.save(new Job("Anonymization B"));
		jobRepository.save(new Job("Synthetization A"));
		jobRepository.save(new Job("Synthetization B"));
	}


}
