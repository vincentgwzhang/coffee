package tina.coffee.system.exceptions.importproduct;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class ImportProductBusinessException extends EntityBusinessException {

    private static final String errorMessageTmpl = "ImportProductBusinessException.message";

    public ImportProductBusinessException() {
        super(errorMessageTmpl, new Object[]{});
    }

    public static Supplier<ImportProductBusinessException> newImportProductNotFoundException(String errorMessage) {
        return () -> new ImportProductBusinessException();
    }
}