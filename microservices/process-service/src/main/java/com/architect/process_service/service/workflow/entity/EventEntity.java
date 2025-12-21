package com.architect.process_service.service.workflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_entity")
public class EventEntity extends BaseEntity {
    @Column(name = "event_id", updatable = false, nullable = false, unique = true)
    private String eventId;

    @PrePersist
    public void generateEventId() {
        if (eventId == null) {
            eventId = UUID.randomUUID().toString();
        }
    }
}
