package tina.coffee.system.exceptions.importproduct;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class ImportProductNotFoundException extends EntityNotFoundException {

    private static String errorMessage = "Import product is not exist";

    public ImportProductNotFoundException() {
        super(errorMessage);
    }

    public static Supplier<ImportProductNotFoundException> newImportProductNotFoundException = () -> new ImportProductNotFoundException();
}