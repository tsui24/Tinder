package com.tinder.tinder.service;

import com.tinder.tinder.exception.AppException;
import com.tinder.tinder.exception.ErrorException;
import com.tinder.tinder.model.Images;
import com.tinder.tinder.model.Users;
import com.tinder.tinder.repository.ImagesRepository;
import com.tinder.tinder.repository.UserRepository;
import com.tinder.tinder.service.impl.IImagesService;
import com.tinder.tinder.utils.UtilsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImagesService implements IImagesService {
    private final ImagesRepository imagesRepository;
    private final UtilsService utilsService;
    private final UserRepository userRepository;
    public ImagesService(ImagesRepository imagesRepository, UtilsService utilsService, UserRepository userRepository) {
        this.imagesRepository = imagesRepository;
        this.utilsService = utilsService;
        this.userRepository = userRepository;
    }

    @Override
    public void addImage(String src) {
        Long userId = utilsService.getUserIdFromToken();
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorException.USER_NOT_EXIST);
        }
        Users user = userOptional.get();
        Images image = new Images();
        image.setUrl(src);
        image.setStatus(1);
        image.setUsers(user);
        imagesRepository.save(image);
    }
}
