package de.ingef.deid.service.arx;

import java.util.UUID;

import de.ingef.deid.service.DeidentificationStartEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArxDeidentificationStartEvent implements DeidentificationStartEvent {

	private final UUID jobId;
}
