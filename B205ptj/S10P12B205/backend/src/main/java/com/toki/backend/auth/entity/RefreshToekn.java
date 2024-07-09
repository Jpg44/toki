package com.toki.backend.auth.entity;

import com.toki.backend.auth.dto.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 리프레시 토큰 엔티티
 */
@Entity
@Table(name = "refresh_token",indexes = @Index(name = "idx_refresh_token",columnList = "refresh_token"))
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class RefreshToekn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;
    String refreshToken;
    @CreatedDate
    LocalDateTime createAt;
    LocalDateTime expireTime;
    Integer snsType;
    @Enumerated(EnumType.STRING)
    Role role;
    String userPk;

    @Builder
    public RefreshToekn(String refreshToken, LocalDateTime expireTime, Integer snsType, Role role, String userPk) {
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
        this.snsType = snsType;
        this.role = role;
        this.userPk = userPk;
    }
}
