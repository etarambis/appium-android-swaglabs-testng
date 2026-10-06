package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.CredentialJson;
import utilities.Logs;

import java.io.File;
import java.io.IOException;

public class JsonReader {
    private static final String CREDENTIALS_PATH = "src/test/resources/data/credenciales.json";

    public static CredentialJson readCredentials() {
        final var objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(new File(CREDENTIALS_PATH), CredentialJson.class);
        } catch (IOException ioException) {
            Logs.error("Error al leer del JSON: %s", ioException.getLocalizedMessage());
            throw new RuntimeException(ioException.getLocalizedMessage());
        }
    }
}