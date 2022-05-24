package com.grupo5.huiapi.modules.event.repository;

import com.grupo5.huiapi.modules.event.entity.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	
	@Query("SELECT e FROM Event e  JOIN e.organizer o WHERE o.id = ?1")
	List<Event> findCreatedEvents(Long user_id);
}
