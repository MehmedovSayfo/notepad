package com.dxc.notepad.service.api;

import com.dxc.notepad.model.Creator;

public interface CreatorService {

    /**
     * Attempts to retrieve a user by his unique username.
     * If the user is not present in the database, he is saved and his reference is returned.
     *
     * @param creator The creator whose username we are searching for.
     * @return The user with this username.
     */
    Creator searchCreator(Creator creator);
}
