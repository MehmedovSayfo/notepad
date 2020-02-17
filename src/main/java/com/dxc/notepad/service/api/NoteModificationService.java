package com.dxc.notepad.service.api;

import com.dxc.notepad.model.NoteModification;

public interface NoteModificationService {

    /**
     * The passed note modification is to be saved into the database,
     * but first its modifier is checked whether he exists in the database or not.
     * If such a user is already present, he is retrieved, otherwise he is saved and retrieved.
     * The note's modifier and modification date are assigned and then the note modification is saved.
     *
     * @param noteModification The note modification to be saved into the database.
     */
    void saveModification(NoteModification noteModification);

}
