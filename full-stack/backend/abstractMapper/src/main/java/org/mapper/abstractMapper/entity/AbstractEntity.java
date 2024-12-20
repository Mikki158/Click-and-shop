package org.mapper.abstractMapper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity implements Serializable {

    Long id;
    LocalDateTime created;
    LocalDateTime updated;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Column(name = "created", updatable = false)
    public LocalDateTime getCreated() {
        return created;
    }

    @Column(name = "updated", insertable = false)
    public LocalDateTime getUpdated() {
        return updated;
    }

    @PrePersist
    public void toCreate() {
        setCreated(LocalDateTime.now());
    }

    @PreUpdate
    public void toUpdate() {
        setUpdated(LocalDateTime.now());
    }
}
