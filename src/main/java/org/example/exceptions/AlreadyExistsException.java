package org.example.exceptions;

public class AlreadyExistsException extends Exception{
    public AlreadyExistsException() {
        super("Aceasta entitate deja exista");
    }
}
