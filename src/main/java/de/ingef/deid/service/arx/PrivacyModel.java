package de.ingef.deid.service.arx;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.deidentifier.arx.criteria.PrivacyCriterion;

@JsonTypeInfo(property = "type", use = JsonTypeInfo.Id.CUSTOM)
@JsonSubTypes({
		@JsonSubTypes.Type(value = PopulationUniqueness.class, name = "POPULATION_UNIQUENESS"),
		@JsonSubTypes.Type(value = KAnonymity.class, name = "K_ANONYMITY")})
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PrivacyModel {

	@NotEmpty
	private Map<String, String> localizedLabels;

	@JsonIgnore
	public abstract PrivacyCriterion getPrivacyCriterion();
}
