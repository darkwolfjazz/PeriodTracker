package com.periodTracker.exceptions;


import lombok.Getter;


@Getter
public class AppExceptions extends RuntimeException{

    private final int status;

    public AppExceptions(int status,String message) {
        super(message);
        this.status = status;
    }
}
