package com.jung.daysum.repository;

import com.jung.daysum.domain.User;
import com.jung.daysum.domain.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);  // '소셜 타입, 식별자'로 해당 회원을 찾기 위한 메소드 (차후 추가정보 입력 회원가입 가능)
}
