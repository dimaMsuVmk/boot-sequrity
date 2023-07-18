package ru.ivanov.bootmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.ivanov.bootmvc.service.UserService;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final UserService userDetailsService;
    @Autowired
    public AuthProviderImpl(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        String password = authentication.getCredentials().toString();
        if(!password.equals(userDetails.getPassword())) throw new BadCredentialsException("Incorrect password");
        //UsernamePasswordAuthenticationToken implements Authentication
        //этот объект помещается в Cookie сессию и при каждом запросе пользователя будет
        //отправляться с запросом
        return new UsernamePasswordAuthenticationToken(userDetails,password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
