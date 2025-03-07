package com.rafaelmachado.mseventmanagement.repository;

import com.rafaelmachado.mseventmanagement.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
}
