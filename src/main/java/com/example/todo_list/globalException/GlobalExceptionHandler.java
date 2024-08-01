package com.example.todo_list.globalException;

import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseApi> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseApi(Const.STATUS_UNAUTHORIZED, e.getMessage(), Optional.empty()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseApi> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseApi(Const.STATUS_UNAUTHORIZED, e.getMessage(), Optional.empty()));
    }



    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ResponseApi> handleDisabledException(DisabledException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseApi(Const.STATUS_UNAUTHORIZED, e.getMessage(), Optional.empty()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseApi> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseApi(Const.STATUS_FORBIDDEN, e.getMessage(), Optional.empty()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseApi(Const.STATUS_INTERNAL_ERROR, e.getMessage(), Optional.empty()));
    }

}
