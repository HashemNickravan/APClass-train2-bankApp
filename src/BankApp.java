import java.util.ArrayList;
import java.util.Scanner;

public class BankApp {

    static ArrayList<String[]> users = new ArrayList<>();
    static String[] currentUser = null;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {

            String input = sc.nextLine();
            String[] parts = input.split(" ", 2);
            String command = parts[0];

            switch (command) {

                case "register":
                    if (parts.length < 2) {
                        System.out.println("Error: Invalid register command.");
                        break;
                    }
                    register(parts[1]);
                    break;

                case "login":
                    if (parts.length < 2) {
                        System.out.println("Error: Invalid login command.");
                        break;
                    }
                    login(parts[1]);
                    break;

                case "show":
                    if (parts.length < 2 || !parts[1].equals("balance")) {
                        System.out.println("Error: Unknown command.");
                        break;
                    }
                    showBalance();
                    break;

                case "deposit":
                    if (parts.length < 2) {
                        System.out.println("Error: Invalid deposit command.");
                        break;
                    }
                    deposit(parts[1]);
                    break;

                case "withdraw":
                    if (parts.length < 2) {
                        System.out.println("Error: Invalid withdraw command.");
                        break;
                    }
                    withdraw(parts[1]);
                    break;

                case "transfer":
                    if (parts.length < 2) {
                        System.out.println("Error: Invalid transfer command.");
                        break;
                    }
                    transfer(parts[1]);
                    break;

                case "logout":
                    logout();
                    break;

                case "exit":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Error: Unknown command.");
            }
        }
    }

    public static void register(String args) {

        String[] parts = args.split(" ", 5);

        if (parts.length < 5) {
            System.out.println("Error: Missing arguments.");
            return;
        }

        String username = parts[0];
        String password = parts[1];
        String fullName = parts[2];
        String phoneNumber = parts[3];
        String email = parts[4];

        if (usernameExists(username)) {
            System.out.println("Error: username already exists.");
            return;
        }

        if (!isValidPassword(password)) {
            System.out.println("Error: Invalid password.");
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Error: Invalid phone number.");
            return;
        }

        if (!isValidEmail(email)) {
            System.out.println("Error: Invalid email.");
            return;
        }

        String cardNumber = generateCardNumber();
        String balance = "0";

        users.add(new String[]{username, password, fullName, cardNumber, phoneNumber, email, balance});

        System.out.println("Registered successfully.");
        System.out.println("Assigned card number: " + cardNumber);
    }

    public static void login(String args) {

        String[] parts = args.split(" ", 2);

        if (parts.length < 2) {
            System.out.println("Error: Missing username or password.");
            return;
        }

        String username = parts[0];
        String password = parts[1];

        for (String[] user : users) {
            if (user[0].equals(username) && user[1].equals(password)) {
                currentUser = user;
                System.out.println("Login successful.");
                return;
            }
        }

        System.out.println("Error: Invalid username or password.");
    }

    public static void showBalance() {

        if (currentUser == null) {
            System.out.println("Error: You should login first.");
            return;
        }

        System.out.println("Current balance: " + currentUser[6]);
    }

    public static void deposit(String arg) {

        if (currentUser == null) {
            System.out.println("Error: You should login first.");
            return;
        }

        try {
            double amount = Double.parseDouble(arg);
            double newBalance = Double.parseDouble(currentUser[6]) + amount;
            currentUser[6] = String.valueOf(newBalance);
            System.out.println("Deposit successful. Current balance: " + currentUser[6]);
        } catch (Exception e) {
            System.out.println("Error: Invalid amount.");
        }
    }

    public static void withdraw(String arg) {

        if (currentUser == null) {
            System.out.println("Error: You should login first.");
            return;
        }

        try {
            double amount = Double.parseDouble(arg);
            double currentBalance = Double.parseDouble(currentUser[6]);

            if (amount > currentBalance) {
                System.out.println("Error: insufficient balance.");
                return;
            }

            double newBalance = currentBalance - amount;
            currentUser[6] = String.valueOf(newBalance);
            System.out.println("Withdrawal successful. Current balance: " + currentUser[6]);
        } catch (Exception e) {
            System.out.println("Error: Invalid amount.");
        }
    }

    public static void transfer(String args) {

        if (currentUser == null) {
            System.out.println("Error: You should login first.");
            return;
        }

        String[] parts = args.split(" ", 2);

        if (parts.length < 2) {
            System.out.println("Error: Missing arguments.");
            return;
        }

        String cardNumber = parts[0];
        double amount;

        try {
            amount = Double.parseDouble(parts[1]);
        } catch (Exception e) {
            System.out.println("Error: Invalid amount.");
            return;
        }

        double currentBalance = Double.parseDouble(currentUser[6]);

        if (amount > currentBalance) {
            System.out.println("Error: insufficient balance.");
            return;
        }

        for (String[] user : users) {
            if (user[3].equals(cardNumber)) {
                double targetBalance = Double.parseDouble(user[6]) + amount;
                user[6] = String.valueOf(targetBalance);
                currentUser[6] = String.valueOf(currentBalance - amount);
                System.out.println("Transferred successfully.");
                return;
            }
        }

        System.out.println("Error: invalid card number.");
    }

    public static void logout() {

        if (currentUser == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }

        currentUser = null;
        System.out.println("Logout successful.");
    }

    public static boolean usernameExists(String username) {

        for (String[] user : users) {
            if (user[0].equals(username)) return true;
        }

        return false;
    }

    public static boolean isValidPassword(String password) {

        if (password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (c == '@' || c == '!' || c == '&' || c == '$' || c == '؟') hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {

        if (phoneNumber.length() != 11) return false;
        if (!phoneNumber.startsWith("09")) return false;

        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) return false;
        }

        return true;
    }

    public static boolean isValidEmail(String email) {

        int atIndex = email.indexOf("@");

        if (atIndex <= 0) return false;
        if (!email.endsWith("aut.com")) return false;

        return true;
    }

    public static String generateCardNumber() {

        String cardNumber;

        do {
            cardNumber = "6037";

            for (int i = 0; i < 12; i++) {
                cardNumber += (int) (Math.random() * 10);
            }

        } while (cardExists(cardNumber));

        return cardNumber;
    }

    public static boolean cardExists(String cardNumber) {

        for (String[] user : users) {
            if (user[3].equals(cardNumber)) return true;
        }

        return false;
    }
}
