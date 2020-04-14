package com.github.mrlalonde.hateoas;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ExampleUuidRepository extends CrudRepository<ExampleUuid, UUID> {
}
