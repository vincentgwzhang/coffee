package tina.coffee.system.exceptions.importhistory;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class ImportHistoryNotFoundException extends EntityNotFoundException {

    private static String errorMessage = "Import history is not exist";

    public ImportHistoryNotFoundException() {
        super(errorMessage);
    }

    public static Supplier<ImportHistoryNotFoundException> newImportHistoryNotFoundException = () -> new ImportHistoryNotFoundException();
}