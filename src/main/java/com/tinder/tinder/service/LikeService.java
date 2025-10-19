package com.tinder.tinder.service;

import com.tinder.tinder.enums.StatusName;
import com.tinder.tinder.exception.AppException;
import com.tinder.tinder.exception.ErrorException;
import com.tinder.tinder.model.Likes;
import com.tinder.tinder.model.Matches;
import com.tinder.tinder.model.Users;
import com.tinder.tinder.repository.LikeRepository;
import com.tinder.tinder.repository.MatchRepository;
import com.tinder.tinder.repository.UserRepository;
import com.tinder.tinder.service.impl.ILikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.tinder.tinder.utils.UtilsService;

import java.util.Optional;

@Service
@Slf4j
public class LikeService implements ILikeService {
    private final UtilsService utilsService;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final MatchRepository matchRepository;
    public LikeService(UtilsService utilsService, UserRepository userRepository, LikeRepository likeRepository,
                       MatchRepository matchRepository) {
        this.utilsService = utilsService;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public void likeAnotherPeople(Long idPeople, Integer status) {
        Long userId = utilsService.getUserIdFromToken();
        Optional<Users> userFromOptional = userRepository.findById(userId);
        Optional<Users> usersToOptional = userRepository.findById(idPeople);
        if (userFromOptional.isEmpty()) {
            throw new AppException(ErrorException.USER_NOT_EXIST);
        }
        if (usersToOptional.isEmpty()) {
            throw new AppException(ErrorException.USER_NOT_EXIST);
        }
        if (!likeRepository.existsByFromUserAndToUser(usersToOptional.get(), userFromOptional.get())) {
            log.info("user {} đã like user {}", userFromOptional.get().getUsername(), usersToOptional.get().getUsername());
            Likes likes = new Likes();
            likes.setFromUser(userFromOptional.get());
            likes.setToUser(usersToOptional.get());
            if (status == 0) {
                likes.setStatus(StatusName.LIKE);
            } else if (status == 1) {
                likes.setStatus(StatusName.DISLIKE);
            } else {
                likes.setStatus(StatusName.SUPERLIKE);
            }
            likeRepository.save(likes);
        } else {
            log.info("Đã match user {} với user {}", usersToOptional.get().getUsername(), userFromOptional.get().getUsername());
            Matches matches = new Matches();
            matches.setUser1(userFromOptional.get());
            matches.setUser2(usersToOptional.get());
            matches.setActive(true);
            matchRepository.save(matches);
        }
    }
}
