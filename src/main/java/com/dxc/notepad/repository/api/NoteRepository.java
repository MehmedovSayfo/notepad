package com.dxc.notepad.repository.api;

import com.dxc.notepad.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteRepository extends CrudRepository<Note, UUID>, PagingAndSortingRepository<Note, UUID> {

    @Query(value = "UPDATE note SET note.deactivated_status = 1 WHERE note.id = ?1", nativeQuery = true)
    void archive(UUID id);

    @Query(value = "SELECT * FROM note WHERE note.deactivated_status = 0",
            countQuery = "SELECT count(*) FROM note WHERE note.deactivated_status = 0",
            nativeQuery = true)
    Page<Note> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM note WHERE note.deactivated_status = 1",
            countQuery = "SELECT count(*) FROM note WHERE note.deactivated_status = 1",
            nativeQuery = true)
    Page<Note> findAllArchived(Pageable pageable);

}
