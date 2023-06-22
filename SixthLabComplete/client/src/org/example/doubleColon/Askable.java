package org.example.doubleColon;

import org.example.exceptions.InvalidDataException;

/**
 * Interfaces for Double colon implementation
 * @param <T>
 */
@FunctionalInterface
public interface Askable <T>{
    T ask() throws  InvalidDataException;
}
