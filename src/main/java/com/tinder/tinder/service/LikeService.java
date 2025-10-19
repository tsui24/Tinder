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
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.tinder.tinder.Utils.UtilsService;

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
        Users userFrom = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorException.USER_NOT_EXIST));
        Users userTo = userRepository.findById(idPeople)
                .orElseThrow(() -> new AppException(ErrorException.USER_NOT_EXIST));
        Optional<Likes> actionFromTargetUser = likeRepository.findByFromUserAndToUser(userTo, userFrom);
        // Nếu đối phương đã dislike mình VÀ hành động hiện tại của mình cũng là dislike -> không làm gì cả
        if (actionFromTargetUser.isPresent() &&
                actionFromTargetUser.get().getStatus() == StatusName.DISLIKE && status == 1) {
            log.info("Đối phương đã DISLIKE bạn, và bạn cũng DISLIKE lại. Không có hành động mới.");
            return;
        }
        // Nếu đối phương đã LIKE mình VÀ hành động hiện tại của mình là LIKE -> Tạo match
        if (actionFromTargetUser.isPresent() &&
                actionFromTargetUser.get().getStatus() == StatusName.LIKE && status == 0) {
            log.info("MATCH! {} đã like lại {}", userFrom.getUsername(), userTo.getUsername());
            // xóa lượt like mà họ đã like mình trước vì 2 like đã tạo ra match
            likeRepository.delete(actionFromTargetUser.get());
            Matches matches = new Matches();
            matches.setUser1(userFrom);
            matches.setUser2(userTo);
            matches.setActive(true);
            matchRepository.save(matches);
            return;
        }

        //xử lý trường hợp đối phương đã dislike mình, nhưng mình lại like họ.
        log.info("Ghi nhận hành động từ {} tới {}.", userFrom.getUsername(), userTo.getUsername());
        Likes myAction = new Likes();
        myAction.setFromUser(userFrom);
        myAction.setToUser(userTo);

        if (status == 0) {
            myAction.setStatus(StatusName.LIKE);
        } else if (status == 1) {
            myAction.setStatus(StatusName.DISLIKE);
        } else {
            myAction.setStatus(StatusName.SUPERLIKE);
        }
        likeRepository.save(myAction);
    }

}
