package com.example.todo_list.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class ResponseApi {
    private int code;
    private String message;
    private Optional data;

    public ResponseApi(int code, String message, Optional data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
