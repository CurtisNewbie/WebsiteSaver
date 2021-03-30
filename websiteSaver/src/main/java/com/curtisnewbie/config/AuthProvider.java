package com.curtisnewbie.config;

import com.curtisnewbie.dao.UserEntity;
import com.curtisnewbie.services.api.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Customer authentication provider
 *
 * @author yongjie.zhuang
 */
@Component
public class AuthProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder sha256PwEncoder = new MessageDigestPasswordEncoder("SHA-256");

    public AuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.isAuthenticated())
            return authentication;

        String username = authentication.getName();
        UserEntity user = userService.loadUserByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User '" + username + "' not found");

        String password = authentication.getCredentials().toString();
        if (sha256PwEncoder.matches(password.concat(user.getSalt()), user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(authentication.getName(),
                    authentication.getCredentials(),
                    Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
        }
        throw new BadCredentialsException("User '" + username + "' not authenticated due to bad credential");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean result = UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        return result;
    }
}
