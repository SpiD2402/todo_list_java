package com.example.todo_list.service.impl;

import com.example.todo_list.dto.AuthSignIn;
import com.example.todo_list.dto.AuthSignUp;
import com.example.todo_list.entity.RoleEntity;
import com.example.todo_list.entity.Token;
import com.example.todo_list.entity.UserEntity;
import com.example.todo_list.message.Const;
import com.example.todo_list.repository.RoleDao;
import com.example.todo_list.repository.UserDao;
import com.example.todo_list.response.ResponseApi;
import com.example.todo_list.security.JwtService;
import com.example.todo_list.security.JwtUserDetailsService;
import com.example.todo_list.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(UserDao userDao, RoleDao roleDao, JwtUserDetailsService jwtUserDetailsService, AuthenticationManager authenticationManager, JwtService jwtService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public ResponseApi signIn(AuthSignIn authSignIn) {
        UserDetails userDetails;
        try {
            userDetails = this.jwtUserDetailsService.loadUserByUsername(authSignIn.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("El usuario no existe.");
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authSignIn.getUsername(), authSignIn.getPassword())
            );
            final String token = this.jwtService.generateToken(userDetails);
            String role = auth.getAuthorities().toString().replaceAll("[\\[\\]]", "");
            log.info(role);
            Token keyToken = new Token();
            keyToken.setToken(token);
            return   new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS, Optional.of(keyToken));
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciales incorrectas. Por favor, verifica tu  contraseña.");
        } catch (DisabledException e) {
            throw new DisabledException("La cuenta está deshabilitada. Por favor, contacta al administrador.");
        }
    }

    @Transactional
    @Override
    public ResponseApi signUp(AuthSignUp authSignUp) throws Exception {
        if (userDao.existsByEmailOrUsername(authSignUp.getEmail(),authSignUp.getUsername()) >0)
        {
            throw  new Exception("Username or Email exists");
        }
        try{
            UserEntity user = toEntity(authSignUp);
            userDao.save(user);
            return new ResponseApi(Const.STATUS_CREATED,Const.MSG_CREATED,Optional.of(user));

        }
        catch (Exception e)
        {return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,e.getMessage(),Optional.empty());

        }

    }

    public UserEntity toEntity(AuthSignUp authSignUp)
    {
        Optional<RoleEntity> roleEntity = roleDao.findById(2L);
        UserEntity user = new UserEntity();
        user.setRole(roleEntity.get());
        user.setPassword(encryptPassword(authSignUp.getPassword()));
        user.setUsername(authSignUp.getUsername());
        user.setName(authSignUp.getName());
        user.setLast_name(authSignUp.getLast_name());
        user.setEmail(authSignUp.getEmail());
        user.setStatus(true);
        return  user;
    }
    public String encryptPassword(String password)
    {
        String passwordEncoder = bCryptPasswordEncoder.encode(password);
        return  passwordEncoder;
    }

}
