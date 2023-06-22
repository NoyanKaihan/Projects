package org.example.doubleColon;

import org.example.console.ConsoleColor;
import org.example.exceptions.InvalidDataException;

/**
 * Class for Double Colon implementation
 *
 * @param <T>
 */
public class Question<T> {
    private Askable<T> askable;
    private T answer;

    public Question(String message, Askable askable) {
        this.askable = askable;
        while (true) {
            try {
                System.out.print(ConsoleColor.MAGENTA_BOLD + message);
                System.out.println(ConsoleColor.RESET);
                T ans = this.askable.ask();
                answer = ans;
                break;
            } catch (InvalidDataException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public T getAnswer() {
        return answer;
    }
}
