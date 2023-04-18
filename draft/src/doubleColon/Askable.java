package doubleColon;

import exceptions.DataException;

/**
 * Interfaces for Double colon implementation
 * @param <T>
 */
@FunctionalInterface
public interface Askable <T>{
    T ask()throws DataException;
}
