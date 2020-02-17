package com.dxc.notepad.repository.api;

import com.dxc.notepad.model.Creator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreatorRepository extends CrudRepository<Creator, UUID> {

    @Query(value = "SELECT * FROM creator WHERE creator.username = ?1", nativeQuery = true)
    Creator searchByUsername(String username);
}
