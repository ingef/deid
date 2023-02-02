package de.ingef.deid.service.mostlyai;

import java.util.UUID;

import de.ingef.deid.service.DeidentificationStartEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MostlyAISythetizationStartEvent implements DeidentificationStartEvent {

	private final UUID jobId;
}
