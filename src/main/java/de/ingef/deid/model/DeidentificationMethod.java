package de.ingef.deid.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.ingef.deid.service.DeidentificationStartEvent;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = ArxAnonymizationMethod.class, name = "ARX"),
		@JsonSubTypes.Type(value = MostlyAiSynthetizationMethod.class, name = "MOSTLY_AI")})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "deidentification_type")
public abstract class DeidentificationMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private UUID id;

	@JsonBackReference
	@OneToOne
	@Getter(AccessLevel.PROTECTED)
	private Job job;

	public final UUID getJobId() {
		return job.getId();
	}

	public abstract DeidentificationStartEvent createEvent();
}
