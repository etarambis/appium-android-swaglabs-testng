package models;

import net.datafaker.Faker;

public class User {
    private final String name;
    private final String lastname;
    private final String zipcode;

    public User() {
        final var faker = new Faker();
        name = faker.name().firstName();
        lastname = faker.name().lastName();
        zipcode = faker.address().zipCode();
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getZipcode() {
        return zipcode;
    }
}