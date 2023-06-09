package org.example.exceptions;

public class ConnectionException extends Exception{
    public ConnectionException(){

    }
    public ConnectionException(String message){
        super(message);
    }
}
