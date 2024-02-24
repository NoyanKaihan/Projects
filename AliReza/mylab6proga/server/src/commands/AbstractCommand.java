package commands;

import models.Product;


public abstract class AbstractCommand {
    public AbstractCommand(){}
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public abstract String execute(String argument) throws RuntimeException;


    abstract String getDescription();
}
