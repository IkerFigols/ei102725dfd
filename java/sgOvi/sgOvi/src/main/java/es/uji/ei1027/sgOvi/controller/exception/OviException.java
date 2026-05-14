package es.uji.ei1027.sgOvi.controller.exception;

public class OviException extends RuntimeException {

    private String message;
    private String errorName;

    public OviException(String message, String errorName) {
        this.message = message;
        this.errorName = errorName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

}
