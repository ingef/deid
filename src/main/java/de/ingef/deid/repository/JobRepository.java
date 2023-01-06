package de.ingef.deid.repository;

import java.util.UUID;

import de.ingef.deid.model.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, UUID> {
}
