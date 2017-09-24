package tina.coffee.Verifier;

import tina.coffee.data.model.DesktopEntity;
import tina.coffee.system.exceptions.desktop.DesktopExistException;
import tina.coffee.system.exceptions.desktop.DesktopNotAvailException;

import java.util.Optional;

public class DesktopVerifier {

    public static void verifyIfDesktopExistAndThrow(Optional<DesktopEntity> entity) {
        entity.ifPresent(
                en -> {
                    throw new DesktopExistException(en.getDeskNo());
                }
        );
    }

    public static void verifyIfDesktopNotExistAndThrow(Optional<DesktopEntity> desktopEntity, Integer desktopNumber) {
        desktopEntity.orElseThrow(DesktopExistException.newDesktopExistException(desktopNumber));
    }

    public static void verifyIfDesktopIsDisableAndThrow(Optional<DesktopEntity> desktopEntity) {
        desktopEntity.ifPresent(
                entity -> {
                    if(!entity.isDeskEnable()) {
                        throw new DesktopNotAvailException(DesktopNotAvailException.REJECT_IS_DISABLED, entity.getDeskNo());
                    }
                }
        );
    }

    public static void verifyIfDesktopIsOccupiedAndThrow(Optional<DesktopEntity> desktopEntity) {
        desktopEntity.ifPresent(
                entity -> {
                    if(entity.isDeskOccupied()) {
                        throw new DesktopNotAvailException(DesktopNotAvailException.REJECT_IS_OCCUPIED, entity.getDeskNo());
                    }
                }
        );
    }

    public static void verifyIfDesktopIsNotOccupiedAndThrow(Optional<DesktopEntity> desktopEntity) {
        desktopEntity.ifPresent(
                entity -> {
                    if(!entity.isDeskOccupied()) {
                        throw new DesktopNotAvailException(DesktopNotAvailException.REJECT_IS_NOT_OCCUPIED, entity.getDeskNo());
                    }
                }
        );
    }

    public static void newCreateOrderProcedure(Optional<DesktopEntity> desktopEntity, Integer desktopNumber) {
        verifyIfDesktopNotExistAndThrow(desktopEntity, desktopNumber);
        verifyIfDesktopIsDisableAndThrow(desktopEntity);
        verifyIfDesktopIsOccupiedAndThrow(desktopEntity);
    }

    public static void appendExistOrderProcedure(Optional<DesktopEntity> desktopEntity, Integer desktopNumber) {
        verifyIfDesktopNotExistAndThrow(desktopEntity, desktopNumber);
        verifyIfDesktopIsDisableAndThrow(desktopEntity);
    }

    public static void closeOrderProcedure(Optional<DesktopEntity> desktopEntity, Integer desktopNumber) {
        verifyIfDesktopNotExistAndThrow(desktopEntity, desktopNumber);
        verifyIfDesktopIsDisableAndThrow(desktopEntity);
        verifyIfDesktopIsNotOccupiedAndThrow(desktopEntity);
    }

}
