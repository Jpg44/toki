package com.toki.backend.auth.entity;

import com.toki.backend.auth.dto.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="member")
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class User {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@GeneratedValue(strategy = GenerationType.UUID)
	String userPk;
	
	@Column(nullable = false,unique = true)
	String userId;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,length = 20)
	Role userRole;
	
	@Column(nullable = false, length = 40)
	String userName;

	String userNickName;
	@Column(nullable = true)
	String userEmail;
	String userTag;
	Integer snsType;

	@Column(updatable = false)
	@CreatedDate
	LocalDateTime createAt;
	
	@LastModifiedDate
	LocalDateTime updateAt;
	@GeneratedValue(generator = "0")
	Integer isDelete;
	LocalDateTime deleteAt;
	String birthYear;
	String profileImageUrl;



	@Builder
	public User(String userPk,String userNickName, String birthYear,String profileImageUrl, String userId, Role userRole, String userName, String userEmail, String userTag, Integer snsType) {
		this.userPk = userPk;
		this.userId = userId;
		this.userRole = userRole;
		this.userName = userName;
		this.userNickName = userNickName;
		this.userEmail = userEmail;
		this.birthYear=birthYear;
		this.snsType= snsType;
		this.profileImageUrl=profileImageUrl;
		this.userTag = userTag;
	}
}
