package br.gov.ce.detran.vistoriacfcapi.exception;

public class CnpjUniqueViolationException extends RuntimeException {

    public CnpjUniqueViolationException (String message) {
        super(message);
    }
    
}
