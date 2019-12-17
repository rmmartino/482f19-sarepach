package com.example.sarepach;

import org.junit.Test;


public class TestUser {
    String email = "cnmeier@loyola.edu";
    User user = new User(email);

    public void testUpdateAddress() {
        User user = new User(email);
        user.UpdateAddress(user.StreetName, user.City, user.State, user.HouseNumber, user.ZipCode);

    }
}
