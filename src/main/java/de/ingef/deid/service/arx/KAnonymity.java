package de.ingef.deid.service.arx;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.deidentifier.arx.criteria.PrivacyCriterion;

@Getter
@Setter(AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KAnonymity extends PrivacyModel {

	@Min(1)
	private int k;

	@Override
	public PrivacyCriterion getPrivacyCriterion() {
		return new org.deidentifier.arx.criteria.KAnonymity(k);

	}
}
