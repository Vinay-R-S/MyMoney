package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {
    private String name, email, password, dob, phoneNumber, aadhaar, bank, accountType, branch, address;
    private double amount;

    public User(String name, String email, String password, String dob, String phoneNumber,
                String aadhaar, String bank, String accountType, String branch, String address, double amount) {
        this.name = name;
        this.email = email;
        this.password = PasswordUtil.hashPassword(password);
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.aadhaar = aadhaar;
        this.bank = bank;
        this.accountType = accountType;
        this.branch = branch;
        this.address = address;
        this.amount = amount;
    }

    public static boolean isValidName(String name) {
        return name.length() > 8;
    }

    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean isValidPassword(String password) {
        return password.length() > 8 && password.matches(".*\\d.*") &&
               password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") &&
               password.matches(".*\\W.*");
    }

    public static boolean isValidDOB(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dob, formatter);
        return date.isBefore(LocalDate.now());
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    public static boolean isValidAadhaar(String aadhaar) {
        return aadhaar.matches("\\d{12}");
    }

    public static boolean isValidAccountType(String accountType) {
        return accountType.matches("Savings|Current|Salary|Fixed deposit|NRE");
    }

    public static boolean isValidBranch(String branch) {
        return branch.length() >= 5;
    }

    public static boolean isValidAddress(String address) {
        return address.length() >= 10;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getDob() {
        return dob;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public String getBank() {
        return bank;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getBranch() {
        return branch;
    }

    public String getAddress() {
        return address;
    }
}