package com.yu.yurentcar.domain.user.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@ToString
public class AdminAuthDto extends User implements OAuth2User {
    private final String username;
    private String name;
    private final Long branchId;
    private final String branchName;
    private Map<String, Object> attributes;

    public AdminAuthDto(String username, String password, Long branchId, String branchName, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username = username;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

}
