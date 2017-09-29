package tina.coffee.system.exceptions.importproduct;

import tina.coffee.system.exceptions.EntityBusinessException;

public class ImportProductBusinessException extends EntityBusinessException {

    public static final String errorMessageNameTmpl = "ImportProductBusinessException.name.message";
    public static final String errorMessageHistTmpl = "ImportProductBusinessException.history.message";

    public ImportProductBusinessException(String messageTemplate) {
        super(messageTemplate, new Object[]{});
    }
}