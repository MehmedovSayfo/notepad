package com.dxc.notepad.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id", nullable = false)
    @Valid
    private Creator creator;

    @NotBlank(message = "Do not leave a blank note, please! :-)")
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "deactivated_status", nullable = false)
    private boolean isArchived = false;

    @OneToMany(mappedBy = "modifiedNote")
    private List<NoteModification> modificationHistory;

    public Note() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public boolean isNotArchived() {
        return !isArchived;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<NoteModification> getModificationHistory() {
        return modificationHistory;
    }

}
