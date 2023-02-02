package de.ingef.deid.service;

import java.util.UUID;

public interface DeidentificationStartEvent {

	UUID getJobId();
}
