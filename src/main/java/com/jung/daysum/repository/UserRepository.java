package com.jung.daysum.repository;

import com.jung.daysum.domain.User;
import com.jung.daysum.domain.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<User> findByConnectCode(String connectCode);

    boolean existsByConnectCode(String connectCode);
}
