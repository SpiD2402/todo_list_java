package com.example.todo_list.security;

import com.example.todo_list.repository.UserDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    public JwtUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userDao.findByUsername(username).map(

                user -> {
                        final var authorities = new SimpleGrantedAuthority(user.getRole().getName());
                        return new User(user.getUsername(),user.getPassword(), Collections.singletonList(authorities));
                }

        ).orElseThrow(()-> new UsernameNotFoundException("User not exist"));
    }
}
