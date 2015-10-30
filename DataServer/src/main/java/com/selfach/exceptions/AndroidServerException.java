package com.selfach.exceptions;

/**
 * By gekoreed on 9/12/15.
 */
public class AndroidServerException extends Exception {
    public String result;

    public AndroidServerException(String error){
        this.result = error;
    }

    @Override
    public String getMessage(){
        return result;
    }
}
