package data;

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
}
