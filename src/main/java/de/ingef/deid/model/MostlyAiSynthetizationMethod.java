package de.ingef.deid.model;

import de.ingef.deid.service.DeidentificationStartEvent;
import de.ingef.deid.service.mostlyai.MostlyAISythetizationStartEvent;

public class MostlyAiSynthetizationMethod extends DeidentificationMethod {

	@Override
	public DeidentificationStartEvent createEvent() {
		return new MostlyAISythetizationStartEvent(getJobId());
	}
}
