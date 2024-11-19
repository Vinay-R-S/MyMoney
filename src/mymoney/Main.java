package mymoney;

import gui.LoginPage;
import models.UserDatabase;

public class Main {
    public static void main(String[] args) {
        // Initialize the database (this could be in-memory for now, or connected to an actual database)
        UserDatabase database = new UserDatabase();

        // Display the login page
        LoginPage loginPage = new LoginPage(database);
        loginPage.setVisible(true);
    }
}
