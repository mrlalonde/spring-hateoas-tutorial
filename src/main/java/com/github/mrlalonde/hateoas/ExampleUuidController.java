package com.github.mrlalonde.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/examples")
public class ExampleUuidController {

    @Autowired
    private ExampleUuidRepository exampleUuidRepository;

    @PostMapping("")
    ExampleUuid postExample(@RequestBody ExampleUuid exampleUuid) {
        return exampleUuidRepository.save(exampleUuid);
    }

    @GetMapping("")
    Collection<ExampleUuid> getExamples() {
        return StreamSupport.stream(exampleUuidRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    ResponseEntity<ExampleUuid> get(@PathVariable("uuid") UUID uuid) {
        return exampleUuidRepository.findById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
