package tina.coffee.system.exceptions.importhistorysummary;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class ImportHistorySummaryNotFoundException extends EntityNotFoundException {

    private static String errorMessage = "Import history summary is not exist";

    public ImportHistorySummaryNotFoundException() {
        super(errorMessage);
    }

    public static Supplier<ImportHistorySummaryNotFoundException> newImportHistorySummaryNotFoundException = () -> new ImportHistorySummaryNotFoundException();
}