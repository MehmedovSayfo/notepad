package com.dxc.notepad.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "creator")
public class Creator {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Pattern(regexp = "[A-Z][a-z]{2,20}",
            message = "Invalid username!")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @OneToMany(mappedBy = "creator")
    private Collection<Note> createdNotes;

    @OneToMany(mappedBy = "modifier")
    private Collection<NoteModification> modifiedNotes;

    public Creator() {
    }

    public Creator(@Pattern(regexp = "[A-Z][a-z]{2,20}",
            message = "Invalid username!") String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
