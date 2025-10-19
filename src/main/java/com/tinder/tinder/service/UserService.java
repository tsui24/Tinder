package com.tinder.tinder.service;

import com.tinder.tinder.dto.request.CreateInforUser;
import com.tinder.tinder.dto.request.RegisterRequest;
import com.tinder.tinder.dto.response.UserMatchResult;
import com.tinder.tinder.exception.AppException;
import com.tinder.tinder.exception.ErrorException;
import com.tinder.tinder.jwt.JwtUtil;
import com.tinder.tinder.model.*;
import com.tinder.tinder.repository.*;
import com.tinder.tinder.enums.RoleName;
import com.tinder.tinder.service.impl.IUserService;
import com.tinder.tinder.Utils.UtilsService;
import com.tinder.tinder.utils.GraphHopperService;
import com.tinder.tinder.utils.OSMService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilsService utilsService;
    private final ImagesService imagesService;
    private final InterestRepository interestRepository;
    private final OSMService osmService;
    private final GraphHopperService graphHopperService;
    private final ImagesRepository imagesRepository;
    private final LikeRepository likeRepository;
    private final MatchRepository matchRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final double WEIGHT_SCORE = 0.7;
    private final double DISTANCE_SCORE = 1 - WEIGHT_SCORE;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UtilsService utilsService,
                       ImagesService imagesService, InterestRepository interestRepository, OSMService osmService,
                       GraphHopperService graphHopperService, ImagesRepository imagesRepository, LikeRepository likeRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.utilsService = utilsService;
        this.imagesService = imagesService;
        this.interestRepository = interestRepository;
        this.osmService = osmService;
        this.graphHopperService = graphHopperService;
        this.imagesRepository = imagesRepository;
        this.likeRepository = likeRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public void createUser(RegisterRequest request) {
        Optional<Users> userOptional = Optional.ofNullable((userRepository.findByUsername(request.getUsername())));
        if(userOptional.isPresent()) {
            throw new AppException(ErrorException.USERNAME_IS_EXISTED);
        }
        if(request.getPassword().compareTo(request.getConfirmPassword()) != 0){
            throw new AppException(ErrorException.CONFIRM_PASS_NOT_VALID);
        }
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(RoleName.ROLE_USER);
        userRepository.save(user);
    }

    @Override
    public void updateInforUser(CreateInforUser inforUser) {
        Long userId = utilsService.getUserIdFromToken();
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setFullName(inforUser.getFullName());
            user.setEmail(inforUser.getEmail());
            user.setAddressLat(inforUser.getAddressLat());
            user.setAddressLon(inforUser.getAddressLon());
            user.setBirthday(inforUser.getBirthday());
            user.setGender(inforUser.getGender());
            user.setInterestedIn(inforUser.getInterestedIn());
//            List<Interests> interestsList = interestRepository.findAllById(inforUser.getInterestIds());
//            user.setInterests(interestsList);
            user.setLocation(osmService.getLocationName(inforUser.getAddressLat(), inforUser.getAddressLon()));
            List<String> images = inforUser.getImages();
            userRepository.save(user);
            for (String image : images) {
                imagesService.addImage(image);
            }
        } else {
            throw new AppException(ErrorException.USER_NOT_EXIST);
        }

    }

    @Override
    public void updateAddressUser(String addressLat, String addressLon) {
        Long userId = utilsService.getUserIdFromToken();
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setAddressLat(addressLat);
            user.setAddressLon(addressLon);
            userRepository.save(user);
        } else {
            throw new AppException(ErrorException.USER_NOT_EXIST);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Long userId = utilsService.getUserIdFromToken();
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorException.USER_NOT_EXIST);
        }
        Users user = userOptional.get();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException(ErrorException.INVALID_OLD_PASSWORD);
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new AppException(ErrorException.NEW_PASSWORD_SAME_AS_OLD);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public Boolean checkUser() {
        Long userID = utilsService.getUserIdFromToken();
        Optional<Users> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if(user.getEmail() != null && !user.getEmail().isEmpty()) {
                return true;
            }
            return false;
        }
        return false;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return (UserDetails) userRepository.findByUsername(username);
//    }

    private List<UserMatchResult> findAllExcept(Long userId, List<Integer> interestedIn, String lat, String lon, double range, Set<Long> idexisted) {

        StringBuilder sqlBuilder = new StringBuilder("""
            SELECT id as userId, full_name as fullName, YEAR(CURDATE()) - YEAR(birthday) as age, location,
                   ST_Distance_Sphere(POINT(address_lon, address_lat), POINT(:lon, :lat)) AS distanceKm
            FROM user
            WHERE ST_Distance_Sphere(POINT(address_lon, address_lat), POINT(:lon, :lat)) < :range
              AND id <> :userId 
              AND gender IN (:interestedIn) 
        """);
        if (idexisted != null && !idexisted.isEmpty()) {
            sqlBuilder.append(" AND id NOT IN (:idexisted)");
        }
        sqlBuilder.append(" ORDER BY distanceKm");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString(), "UserMatchMapping");
        query.setParameter("userId", userId);
        query.setParameter("interestedIn", interestedIn);
        query.setParameter("lat", lat);
        query.setParameter("lon", lon);
        query.setParameter("range", range);
        if (idexisted != null && !idexisted.isEmpty()) {
            query.setParameter("idexisted", idexisted);
        }

        return query.getResultList();
    }

    @Override
    public List<UserMatchResult> findMatches(double maxDistanceKm) {
        Long userID = utilsService.getUserIdFromToken();
        Optional<Users> userOptional = userRepository.findById(userID);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorException.USER_NOT_EXIST);
        }
        Users currentUser = userOptional.get();
        List<Likes> likes = likeRepository.findAllByFromUser(currentUser);
        List<Long> idsLike = new ArrayList<>();
        likes.forEach(i -> idsLike.add(i.getToUser().getId()));
        List<Matches> matches = matchRepository.findAllByUser1OrUser2(currentUser, currentUser);
        List<Long> idsMatch = new ArrayList<>();
        matches.forEach(m -> {
            if (Objects.equals(m.getUser1().getId(), currentUser.getId())) {
                idsMatch.add(m.getUser2().getId());
            } else if (Objects.equals(m.getUser2().getId(), currentUser.getId())) {
                idsMatch.add(m.getUser1().getId());
            }
        });
        Set<Long> idexisted = new HashSet<>();
        idexisted.addAll(idsLike);
        idexisted.addAll(idsMatch);
        // lấy ra danh sách user match dựa trên khoảng cách
        List<Integer> interestedIn = new ArrayList<>();
        if (currentUser.getInterestedIn() == 0) {
            interestedIn.add(1);
            interestedIn.add(2);
        } else {
            interestedIn.add(currentUser.getInterestedIn());
        }
        List<UserMatchResult> allUsers = this.findAllExcept(userID, interestedIn,
                currentUser.getAddressLat(), currentUser.getAddressLon(), maxDistanceKm, idexisted);
        List<Long> userIds = allUsers.stream()
                .map(UserMatchResult::getUserId)
                .toList();
        List<Users> matchedUsers = userRepository.findAllById(userIds);
        //Map vào để tối ưu
        Map<Long, Users> userMap = matchedUsers.stream()
                .collect(Collectors.toMap(Users::getId, u -> u));
        List<UserMatchResult> results = new ArrayList<>();
        //lấy sở thic của từng user để so sánh tỉ lệ match
        for(UserMatchResult matchResult : allUsers) {
            Users other = userMap.get(matchResult.getUserId());
            List<Interests> common = new ArrayList<>(currentUser.getInterests());
            common.retainAll(other.getInterests());
            double interestScore = (double) common.size() /
                    (currentUser.getInterests().size() + other.getInterests().size() - common.size());
            double distanceScore = 1 - (matchResult.getDistanceKm() / maxDistanceKm);
            double finalScore = (WEIGHT_SCORE * interestScore) + (DISTANCE_SCORE * distanceScore);
            matchResult.setFinalScore(finalScore);
            matchResult.setDistanceKm(Math.ceil(distanceScore / 1000));
            matchResult.setImagesList(
                    other.getImages().stream()
                            .map(Images::getUrl)
                            .toList()
            );
            matchResult.setInterestsList(
                    other.getInterests().stream()
                            .map(Interests::getName)
                            .toList()
            );
            results.add(matchResult);
        }
        results.sort((a, b) -> Double.compare(b.getFinalScore(), a.getFinalScore()));
        return results;
    }
}
