package org.example.connection;

public class ResponseMessage implements Response{
    private String message;
    private Status status;
    public ResponseMessage(){
        this.message = "";
        this.status = Status.OK;
    }
    @Override
    public void setMessage(String message){
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public void setStatusToError(){
        this.status = Status.ERROR;
    }
    @Override
    public Status getStatus() {
        return status;
    }
}
