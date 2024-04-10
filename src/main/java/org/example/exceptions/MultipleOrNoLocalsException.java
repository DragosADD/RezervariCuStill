package org.example.exceptions;

public class MultipleOrNoLocalsException extends RuntimeException{
    public MultipleOrNoLocalsException(){
        super("Ori sunt mai multe locale or niciunul");
    }
    public MultipleOrNoLocalsException(String message){
        super(message);
    }
    public MultipleOrNoLocalsException(String message, Throwable cause){
        super(message, cause);
    }
}
