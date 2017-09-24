package tina.coffee.Verifier;

import tina.coffee.data.model.ImportHistoryEntity;
import tina.coffee.data.model.ImportHistorySummaryEntity;
import tina.coffee.system.exceptions.importhistory.ImportHistoryNotFoundException;
import tina.coffee.system.exceptions.importhistorysummary.ImportHistorySummaryNotFoundException;

import java.util.Optional;

public class ImportHistoryVerifier {

    public static void verifyIfImportHistorySummaryExistOrThrow(Optional<ImportHistorySummaryEntity> importHistorySummaryEntityOptional) {
        importHistorySummaryEntityOptional.orElseThrow(ImportHistorySummaryNotFoundException.newImportHistorySummaryNotFoundException);
    }

    public static void verifyIfImportHistoryExistOrThrow(Optional<ImportHistoryEntity> importHistoryEntity) {
        importHistoryEntity.orElseThrow(ImportHistoryNotFoundException.newImportHistoryNotFoundException);
    }

}
