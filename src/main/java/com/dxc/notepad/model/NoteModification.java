package com.dxc.notepad.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "note_modification")
public class NoteModification {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "modifier_id", nullable = false)
    @Valid
    private Creator modifier;

    @NotBlank(message = "Do not leave a blank note, please! :-)")
    @Column(name = "modified_text", nullable = false)
    private String modifiedText;

    @Column(name = "modification_date", nullable = false)
    private LocalDateTime modificationDate;

    @ManyToOne
    @JoinColumn(name = "note_id", nullable = false)
    private Note modifiedNote;

    public NoteModification() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Creator getModifier() {
        return modifier;
    }

    public void setModifier(Creator modifier) {
        this.modifier = modifier;
    }

    public String getModifiedText() {
        return modifiedText;
    }

    public void setModifiedText(String modifiedText) {
        this.modifiedText = modifiedText;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Note getModifiedNote() {
        return modifiedNote;
    }

    public void setModifiedNote(Note modifiedNote) {
        this.modifiedNote = modifiedNote;
    }
}
