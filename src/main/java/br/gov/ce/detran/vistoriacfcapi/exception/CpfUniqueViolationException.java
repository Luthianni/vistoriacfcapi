package br.gov.ce.detran.vistoriacfcapi.exception;

public class CpfUniqueViolationException extends RuntimeException{

    public CpfUniqueViolationException(String message) {
        super(message);
    }

}
