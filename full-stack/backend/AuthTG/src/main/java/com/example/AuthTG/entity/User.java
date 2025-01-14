package com.example.AuthTG.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User{

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String firstName;
    private String username;
    private String photoUrl;
    private String role;

    public User(Long id, String firstName, String username, String photoUrl, String role) {
        this.id = id;
        this.firstName = firstName;
        this.username = username;
        this.photoUrl = photoUrl;
        this.role = role;
    }

    @Id
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

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    @Column(name = "photo_url")
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Column(name = "role")
    public String getRole() {
        return role;
    }
}
