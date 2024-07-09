package com.toki.backend.auth.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAuthDto implements UserDetails, OAuth2User {
 
	private static final long serialVersionUID = 1436240865132149381L;

	private String userId;
 
    private String password;
    
    private String grade;
    private int isDelete;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<GrantedAuthority>auth=new ArrayList<>();
    	auth.add(new SimpleGrantedAuthority(grade));
    	return auth;
	}
 
    @Override
    public String getUsername() {
        return userId;
    }
 
    @Override
    public String getPassword() {
        return password;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
    
        return isDelete==0?true:false;
    }


    @Override
    public String getName() {
        return null;
    }
}