package com.toki.backend.auth.dto.token;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RefreshTokenDto {
	private String userPk;
	private String refreshToken;
	private String expireTime;
	
}
