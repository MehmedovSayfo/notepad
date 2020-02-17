package com.dxc.notepad.service;

import com.dxc.notepad.model.Creator;
import com.dxc.notepad.model.NoteModification;
import com.dxc.notepad.repository.api.NoteModificationRepository;
import com.dxc.notepad.service.api.CreatorService;
import com.dxc.notepad.service.api.NoteModificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoteModificationServiceImpl implements NoteModificationService {

    private CreatorService creatorService;
    private NoteModificationRepository noteModificationRepository;


    public NoteModificationServiceImpl(CreatorService creatorService, NoteModificationRepository noteModificationRepository) {
        this.creatorService = creatorService;
        this.noteModificationRepository = noteModificationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveModification(NoteModification noteModification) {
        Creator modifier = creatorService.searchCreator(noteModification.getModifier());
        noteModification.setModifier(modifier);
        noteModification.setModificationDate(LocalDateTime.now());
        noteModificationRepository.save(noteModification);
    }

}
