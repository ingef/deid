package de.ingef.deid.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReviewService {

	@EventListener
	public void startReviewProcess(DeidentificationCompleteEvent event) {
		log.info("Deidentification completed successfully for job '{}'", event.getJobId());
	}
}
