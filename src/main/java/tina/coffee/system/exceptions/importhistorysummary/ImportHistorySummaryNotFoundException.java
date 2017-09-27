package tina.coffee.system.exceptions.importhistorysummary;

import tina.coffee.system.exceptions.EntityNotFoundException;

import java.util.function.Supplier;

public class ImportHistorySummaryNotFoundException extends EntityNotFoundException {

    private static String errorMessageTmpl = "ImportHistorySummaryNotFoundException.message";

    public ImportHistorySummaryNotFoundException() {
        super(errorMessageTmpl, new Object[]{});
    }

    public static Supplier<ImportHistorySummaryNotFoundException> newImportHistorySummaryNotFoundException = () -> new ImportHistorySummaryNotFoundException();
}