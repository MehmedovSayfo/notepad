package com.dxc.notepad.service;

import com.dxc.notepad.model.Creator;
import com.dxc.notepad.repository.api.CreatorRepository;
import com.dxc.notepad.service.api.CreatorService;
import org.springframework.stereotype.Service;

@Service
public class CreatorServiceImpl implements CreatorService {

    private CreatorRepository creatorRepository;

    public CreatorServiceImpl(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Creator searchCreator(Creator creator) {
        Creator retrieved = creatorRepository.searchByUsername(creator.getUsername());
        if (retrieved == null) {
            retrieved = creatorRepository.save(creator);
        }
        return retrieved;
    }
}
