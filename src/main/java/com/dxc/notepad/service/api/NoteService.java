package com.dxc.notepad.service.api;

import com.dxc.notepad.model.Note;
import com.dxc.notepad.model.NoteModification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NoteService {

    /**
     * The passed note is to be saved into the database, but first its creator is checked whether he exists in the database or not.
     * If such a creator is already present, he is retrieved, otherwise he is saved and retrieved.
     * The note's creator and creation date are assigned and then the note is saved.
     *
     * @param note The note to be saved into the database.
     */
    void saveNote(Note note);

    /**
     * A logical deletion of a note, which is implemented as a deactivation flag being raised.
     *
     * @param note The note to be "deleted"/archived.
     */
    void archiveNote(Note note);

    /**
     * Retrieves a Note object from the database by the passed id or null if no such object exists.
     *
     * @param id The note's id in the database.
     * @return A Note object or null.
     */
    Note findSingleNote(UUID id);

    /**
     * Retrieves all Note objects which are NOT deactivated and adds them to a Page object with set size.
     *
     * @param pageable The interface of the Page object which will contain the content.
     * @return Returns a Page object which contains the content.
     */
    Page<Note> findAllNotes(Pageable pageable);

    /**
     * Retrieves all Note objects which are deactivated and adds them to a Page object with set size.
     *
     * @param pageable The interface of the Page object which will contain the content.
     * @return Returns a Page object which contains the content.
     */
    Page<Note> findAllArchivedNotes(Pageable pageable);

    /**
     * Using the note, which is to be updated, and the created modification, their contents are swapped and
     * the modification is attached to the note and saved to the database.
     *
     * @param note         The note to be updated.
     * @param modification The modification of the note.
     */
    void updateNote(Note note, NoteModification modification);

}
