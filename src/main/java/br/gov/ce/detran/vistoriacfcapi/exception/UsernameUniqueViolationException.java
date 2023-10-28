package br.gov.ce.detran.vistoriacfcapi.exception;

public class UsernameUniqueViolationException extends RuntimeException {

    public UsernameUniqueViolationException(String message) {
        super(message);
    }
    
}
