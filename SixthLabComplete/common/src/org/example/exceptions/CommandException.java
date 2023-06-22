package org.example.exceptions;

public class CommandException extends Exception{
    public CommandException(){
        this("");
    }
    public CommandException(String message){
        super(message);
    }
}
