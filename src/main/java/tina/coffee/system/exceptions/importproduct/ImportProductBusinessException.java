package tina.coffee.system.exceptions.importproduct;

import tina.coffee.system.exceptions.EntityBusinessException;

import java.util.function.Supplier;

public class ImportProductBusinessException extends EntityBusinessException {

    public static String ERR_DUPLICATE = "Already have such product, please check name if duplicate";

    public ImportProductBusinessException(String errorMessage) {
        super(errorMessage);
    }

    public static Supplier<ImportProductBusinessException> newImportProductNotFoundException(String errorMessage) {
        return () -> new ImportProductBusinessException(errorMessage);
    }
}