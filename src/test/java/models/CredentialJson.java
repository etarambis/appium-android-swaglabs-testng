package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CredentialJson {

    @JsonProperty("credentials")
    private Map<String, Credential> credentials;

    public Map<String, Credential> getCredentials() {
        return credentials;
    }
}
