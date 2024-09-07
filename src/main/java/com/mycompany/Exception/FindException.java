package com.mycompany.Exception;

/**
 *
 * @author Ramosz
 */
public class FindException extends Exception {

    /**
     *
     */
    public FindException() {
    }

    /**
     *
     * @param message
     */
    public FindException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public FindException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public FindException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public FindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
