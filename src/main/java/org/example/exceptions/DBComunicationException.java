package org.example.exceptions;

public class DBComunicationException extends Exception{
    public DBComunicationException(){
        super ("There was an issue when a query was executed");
    }

    public DBComunicationException(String message){
        super(message);
    }

    public DBComunicationException(String message, Throwable e){
        super(message, e);
    }
    public DBComunicationException(Throwable e){
        super(e);
    }
}
