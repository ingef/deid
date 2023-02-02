package de.ingef.deid.model;

import de.ingef.deid.service.DeidentificationStartEvent;
import de.ingef.deid.service.arx.ArxDeidentificationStartEvent;
import jakarta.persistence.Entity;

@Entity
public class ArxAnonymizationMethod extends DeidentificationMethod {
	@Override
	public DeidentificationStartEvent createEvent() {
		return new ArxDeidentificationStartEvent(getJobId());
	}
}
