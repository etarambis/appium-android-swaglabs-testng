package data;

import exceptions.FrameworkException;
import models.Credential;

import java.util.Map;

public class DataGiver {
    private static Map<String, Credential> getCredentialMap() {
        return JsonReader.readCredentials().getCredentials();
    }

    private static Credential getCredential(String key) {
        final var credential = getCredentialMap().get(key);
        if (credential == null) {
            throw new FrameworkException("No existe la credencial '" + key + "' en credenciales.json");
        }
        return credential;
    }

    public static Credential getValidCredentials() {
        return getCredential("valid");
    }

    public static Credential getLockedCredentials() {
        return getCredential("locked");
    }

    public static Credential getInvalidCredentials() {
        return getCredential("invalid");
    }
}
