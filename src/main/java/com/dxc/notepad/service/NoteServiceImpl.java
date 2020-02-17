package com.dxc.notepad.service;

import com.dxc.notepad.model.Creator;
import com.dxc.notepad.model.Note;
import com.dxc.notepad.model.NoteModification;
import com.dxc.notepad.repository.api.NoteModificationRepository;
import com.dxc.notepad.repository.api.NoteRepository;
import com.dxc.notepad.service.api.CreatorService;
import com.dxc.notepad.service.api.NoteModificationService;
import com.dxc.notepad.service.api.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {

    private CreatorService creatorService;
    private NoteRepository noteRepository;
    private NoteModificationService noteModificationService;

    public NoteServiceImpl(CreatorService creatorService,
                           NoteRepository noteRepository,
                           NoteModificationService noteModificationService) {
        this.creatorService = creatorService;
        this.noteRepository = noteRepository;
        this.noteModificationService = noteModificationService;
    }

    @Override
    public void saveNote(Note note) {
        Creator creator = creatorService.searchCreator(note.getCreator());
        note.setCreator(creator);
        note.setCreationDate(LocalDateTime.now());
        noteRepository.save(note);
    }

    @Override
    public void archiveNote(Note note) {
        noteRepository.archive(note.getId());
    }

    @Override
    public Note findSingleNote(UUID id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Note> findAllNotes(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    @Override
    public Page<Note> findAllArchivedNotes(Pageable pageable) {
        return noteRepository.findAllArchived(pageable);
    }

    @Override
    public void updateNote(Note note, NoteModification modification) {
        // Content is extracted both from the note and its modification
        String noteText = note.getText();
        String modifiedText = modification.getModifiedText();
        // The modification is then "attached" to the note
        modification.setModifiedNote(note);
        // The contents are swapped
        modification.setModifiedText(noteText);
        note.setText(modifiedText);
        // And the modification is saved
        noteModificationService.saveModification(modification);
    }

}
