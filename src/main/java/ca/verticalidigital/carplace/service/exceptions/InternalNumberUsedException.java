package ca.verticalidigital.carplace.service.exceptions;

public class InternalNumberUsedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InternalNumberUsedException(){
        super("Internal number already used!");
    }
}
