package org.example.connection;

import java.io.Serializable;

public interface Response extends Serializable {
    String getMessage();
    void setMessage(String message);
    Status getStatus();
    void setStatusToError();
}
