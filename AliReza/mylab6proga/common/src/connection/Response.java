package connection;

import java.io.Serializable;

public class Response implements Serializable {
    String message;
    public Response(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
