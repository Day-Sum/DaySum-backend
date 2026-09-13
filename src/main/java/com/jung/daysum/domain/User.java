package com.jung.daysum.domain;

import com.jung.daysum.domain.common.BaseEntity;
import com.jung.daysum.domain.enums.Role;
import com.jung.daysum.domain.enums.SocialType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    private String nickname;

    @Column(name = "social_id")
    private String socialId;  // 소셜 식별값

    @Enumerated(EnumType.STRING)
    private SocialType socialType;  // 소셜 종류

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder(builderClassName = "UserSaveBuilder", builderMethodName = "UserSaveBuilder")
    public User(String email, Role role, SocialType socialType, String socialId, String nickname) {
        this.email = email;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.nickname = nickname;
    }

    public void updateName(String nickname) {
        this.nickname = nickname;
    }

    public void updateRole() {
        this.role = Role.ROLE_USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deleteAccount() {
        this.email = null;
        this.nickname = "(탈퇴한 사용자)";
        this.socialId = null;
        this.refreshToken = null;
    }
}
