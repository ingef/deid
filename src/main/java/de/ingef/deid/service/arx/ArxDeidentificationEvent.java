package de.ingef.deid.service.arx;

import java.util.UUID;

import de.ingef.deid.service.DeidentificationEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArxDeidentificationEvent implements DeidentificationEvent {

	private final UUID jobId;
}
