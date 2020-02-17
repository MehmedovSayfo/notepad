package com.dxc.notepad.repository.api;

import com.dxc.notepad.model.NoteModification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteModificationRepository extends CrudRepository<NoteModification, UUID> {
}
