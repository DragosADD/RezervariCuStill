package org.example.exceptions;

public class MultipleOrNoEntityException extends Exception{
    public MultipleOrNoEntityException(){
        super("Ori exista prea multe entitati or nici o entiatea.");
    }
    public MultipleOrNoEntityException(String message){
        super(message);
    }
    public MultipleOrNoEntityException(String message, Throwable cause){
        super(message, cause);
    }
}
