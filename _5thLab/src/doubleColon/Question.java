package doubleColon;

import console.ConsoleColor;
import exceptions.DataException;

/**
 * Class for Double Colon implementation
 * @param <T>
 */
public class Question <T>{
    private Askable<T> askable;
    private T answer;
    public Question(String message,Askable askable){
        this.askable = askable;
        while(true){
            try {
                System.out.print(ConsoleColor.CYAN_BOLD+message);
                System.out.println(ConsoleColor.RESET);
                T ans = this.askable.ask();
                answer = ans;
                break;
            }catch (DataException exception){
                System.out.println(exception.getMessage());
            }
        }
    }
    public T getAnswer(){
        return answer;
    }
}
