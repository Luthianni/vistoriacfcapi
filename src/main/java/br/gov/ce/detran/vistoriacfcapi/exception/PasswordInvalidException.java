package br.gov.ce.detran.vistoriacfcapi.exception;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String message) {
        super(message);
    }
    
}
