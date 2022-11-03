package top.yumoyumo.yumobot.exception;

/**
 * The type Local runtime exception.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/20 15:47
 */
public class LocalRuntimeException extends RuntimeException {
    /**
     * Instantiates a new Local runtime exception.
     */
    public LocalRuntimeException() {
        super();
    }

    /**
     * Instantiates a new Local runtime exception.
     *
     * @param message the message
     */
    public LocalRuntimeException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Local runtime exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public LocalRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Local runtime exception.
     *
     * @param cause the cause
     */
    public LocalRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Local runtime exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected LocalRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
