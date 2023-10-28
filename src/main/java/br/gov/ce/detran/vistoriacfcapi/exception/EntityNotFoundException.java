package br.gov.ce.detran.vistoriacfcapi.exception;

public class EntityNotFoundException extends RuntimeException  {

    public EntityNotFoundException(String message) {
        super(message);
    }
    
}
