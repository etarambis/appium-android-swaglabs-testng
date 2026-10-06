package data;

import exceptions.FrameworkException;
import models.ErrorMessage;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static Map<String, ErrorMessage> getErrorMessageMap() {
        final var map = new HashMap<String, ErrorMessage>();

        final var errorMessageList = ExcelReader.getErrorMessageList();

        for (var errorMessage : errorMessageList) {
            map.put(errorMessage.getName(), errorMessage);
        }

        return map;
    }

    /** Devuelve el mensaje de error de la clave indicada o falla indicando que falta en el Excel. */
    public static String getErrorMessage(Map<String, ErrorMessage> errorMessageMap, String key) {
        final var errorMessage = errorMessageMap.get(key);
        if (errorMessage == null) {
            throw new FrameworkException("No existe la clave '" + key + "' en la hoja 'mensajes' de dataExcel.xlsx");
        }
        return errorMessage.getMessage();
    }
}
