package de.ingef.deid.model;

import de.ingef.deid.service.DeidentificationEvent;
import de.ingef.deid.service.arx.ArxDeidentificationEvent;
import jakarta.persistence.Entity;

@Entity
public class ArxAnonymizationMethod extends DeidentificationMethod {
	@Override
	public DeidentificationEvent createEvent() {
		return new ArxDeidentificationEvent(getJob().getId());
	}
}
