package Neo.exceptions;

public class ServiceException extends Exception{
    public ServiceException(String errorMessage) {
        super(errorMessage);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }
    public ServiceException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
