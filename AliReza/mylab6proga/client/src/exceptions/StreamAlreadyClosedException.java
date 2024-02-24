package exceptions;

import java.io.EOFException;

public class StreamAlreadyClosedException extends EOFException {
    public StreamAlreadyClosedException(String message){
        System.out.println();
    }
}
