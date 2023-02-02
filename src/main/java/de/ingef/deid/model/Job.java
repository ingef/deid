package de.ingef.deid.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Job {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private UUID id;


	private String name;
	@Setter
	private JobState status;

	@JsonManagedReference
	@OneToOne(cascade = CascadeType.ALL)
	private DeidentificationMethod deidentificationMethod;

	@OneToMany
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<Task> tasks;

	public Job(String name) {
		this.name = name;
	}
}
