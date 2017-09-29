package tina.coffee.Verifier;

import com.google.common.collect.Streams;
import tina.coffee.data.model.ImportProductEntity;
import tina.coffee.rest.dto.ImportProductDTO;
import tina.coffee.system.exceptions.importproduct.ImportProductBusinessException;
import tina.coffee.system.exceptions.importproduct.ImportProductNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ImportProductVerifier {

    public static void verifyIfImportProductExistOrThrow(Optional<ImportProductEntity> importProductEntityOptional) {
        importProductEntityOptional.orElseThrow(ImportProductNotFoundException.newImportProductNotFoundException);
    }

    public static void addImportProductProcedure(List<ImportProductEntity> importProductEntityList, ImportProductDTO dto) {
        Stream<String> cnNameList = importProductEntityList.stream().filter(en -> en.getIpId()!=dto.getIpId()).map(ImportProductEntity::getCnName);
        Stream<String> enNameList = importProductEntityList.stream().filter(en -> en.getIpId()!=dto.getIpId()).map(ImportProductEntity::getEnName);
        Stream<String> spNameList = importProductEntityList.stream().filter(en -> en.getIpId()!=dto.getIpId()).map(ImportProductEntity::getSpName);

        Optional<String> duplicatedName = Streams.concat(cnNameList,enNameList,spNameList).filter(verifyIfImportProductDTOContains(dto)).findAny();
        duplicatedName.ifPresent(str -> {throw new ImportProductBusinessException(ImportProductBusinessException.errorMessageNameTmpl);});
    }

    private static Predicate<String> verifyIfImportProductDTOContains(ImportProductDTO dto) {
        return str -> dto.getCnName().equalsIgnoreCase(str)
                || dto.getEnName().equalsIgnoreCase(str)
                || dto.getSpName().equalsIgnoreCase(str);
    }

}
