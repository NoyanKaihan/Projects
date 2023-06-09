package org.example.exceptions;

public class MissedCommandArgumentException extends InvalidDataException{
    public MissedCommandArgumentException(){
        super("argument is missing");
    }
}
