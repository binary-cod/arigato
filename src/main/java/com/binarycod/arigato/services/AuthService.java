package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Authority;
import com.binarycod.arigato.domain.CustomUser;
import com.binarycod.arigato.repositories.AuthoritiesRepository;
import com.binarycod.arigato.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        return userRepository.findCustomUserByUsername(username);
    }

    public void registerNewUser(CustomUser user){
        Authority authority = authoritiesRepository.findAuthorityByAuthority("USER");

        Authority[] authorities = new Authority[]{authority};

        user.setUsername(user.getUserProfile().getEmail());
        user.setAuthorities(Arrays.asList(authorities));

        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }
}
