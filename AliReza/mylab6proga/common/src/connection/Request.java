package connection;

import models.Product;

import java.io.Serializable;

public class Request implements Serializable {
    String command ;
    String argument ;
    Product product;
    public Request (String command){
        this.command = command;
        this.argument = null;
        this.product = null;
    }
    public Request (String command,String argument){
        this.command = command;
        this.argument = argument;
        this.product = null;
    }
    public Request (String command,String argument,Product product){
        this.command  = command;
        this.argument = argument;
        this.product = product;
    }

    public Request() {

    }

    public void setArgument(String argument) {
        this.argument = argument;

    }

    public String getArgument() {
        return argument;
    }

    public String getCommand() {
        return command;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return
                "command='" + command + '\'' +
                        ", argument='" + argument + '\'' +
                        ", product='" + product+ '\'';
    }
}
