package com.pettrek.backend.auth.security;


import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.auth.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public AuthUserDetailsService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Transactional
    public UserDetails loadByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Пользователь с таким email: " + email + " не найден"));
        return UserPrincipal.create(user);
    }
    @Transactional
    public UserDetails loadById(Long id){
        User user = userRepo.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Пользователь с таким id: "+ id + " не найден"));
        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким email: " + email + " не найден"));
        return UserPrincipal.create(user);
    }
}
