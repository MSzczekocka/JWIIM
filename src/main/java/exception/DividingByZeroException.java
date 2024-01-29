package exception;

/**
 * @author Martyna Szczekocka
 * @version 1.0
 * Class containing custom exceptions
 */

public class DividingByZeroException extends ArithmeticException {
    /**
     * Constructor for exception
     */
    public DividingByZeroException() {
        super("Dividing By Zero");
    }
}
