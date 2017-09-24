package tina.coffee.system.exceptions.security;

import tina.coffee.system.exceptions.EntityBusinessException;

public class CoffeeAccessDeniedException extends EntityBusinessException {

    public CoffeeAccessDeniedException() {
        super("access deny");
    }
}
