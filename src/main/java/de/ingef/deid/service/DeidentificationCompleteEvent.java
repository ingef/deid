package de.ingef.deid.service;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DeidentificationCompleteEvent {
	private final UUID jobId;
}
