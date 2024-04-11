package org.example.exceptions;

public class NoTheSameException extends Exception {
    public NoTheSameException(String message){
        super("Not the same objects, unable to Proceed");
    };
}
