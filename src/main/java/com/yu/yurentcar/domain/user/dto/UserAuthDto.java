package com.yu.yurentcar.domain.user.dto;

import com.yu.yurentcar.domain.user.entity.JoinType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@ToString
public class UserAuthDto extends User implements OAuth2User {
    private final String username;
    private String name;
    private LocalDateTime birthday;
    private final JoinType joinType;
    private Map<String, Object> attributes;

    @Builder(builderMethodName = "customBuilder")
    public UserAuthDto(String username, String password, String name, LocalDateTime birthday,
                       JoinType type,
                       Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        this(username, password, type, authorities);
        this.name = name;
        this.birthday = birthday;
        this.attributes = attributes;
    }

    public UserAuthDto(String username, String password, JoinType type, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username = username;
        this.joinType = type;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

}
