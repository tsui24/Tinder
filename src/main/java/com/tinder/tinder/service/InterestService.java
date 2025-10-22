package com.tinder.tinder.service;

import com.tinder.tinder.dto.request.InterestCreate;
import com.tinder.tinder.exception.AppException;
import com.tinder.tinder.exception.ErrorException;
import com.tinder.tinder.model.Interests;
import com.tinder.tinder.repository.InterestRepository;
import com.tinder.tinder.service.impl.IInterestService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;

@Service
public class InterestService implements IInterestService {

    private final InterestRepository interestRepository;

    public InterestService(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Override
    public void addInterest(InterestCreate interestCreate) {
        Interests newInterest = new Interests();
        if(interestCreate.getName() == null || interestCreate.getName().isEmpty()) {
            throw new AppException(ErrorException.INTEREST_NAME_NOT_BLANK);
        }
        Interests exist = interestRepository.getByName(interestCreate.getName());
        if (exist == null) {
            newInterest.setName(interestCreate.getName().trim());
            interestRepository.save(newInterest);
        } else {
            throw new AppException(ErrorException.NAME_INTEREST_EXISTS);
        }
    }

    @Override
    public void updateInterest(Long id, String name) {

        Interests interest = interestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorException.INTERSET_NOT_EXIST));

        if (name == null || name.trim().isEmpty()) {
            throw new AppException(ErrorException.INTEREST_NAME_NOT_BLANK);
        }

        String trimmedName = name.trim();

        if (trimmedName.equals(interest.getName())) {
            return;
        }

        Interests exist = interestRepository.getByName(name);
        if (exist == null) {
            interest.setName(name.trim());
            interestRepository.save(interest);
        } else {
            throw new AppException(ErrorException.NAME_INTEREST_EXISTS);
        }
        interestRepository.save(interest);
    }

    @Override
    public List<Interests> getAllInterests() {
        return interestRepository.findAll();
    }
}
