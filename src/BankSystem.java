import java.util.HashMap;

public class BankSystem {

    private HashMap<String, User> usersByUsername = new HashMap<>();
    private HashMap<String, User> usersByCardNumber = new HashMap<>();
    private User currentUser = null;

    public void register(String username, String password, String fullName,
                         String phone, String email) {

        if (usersByUsername.containsKey(username)) {
            System.out.println("Error: username already exists.");
            return;
        }

        if (!isValidPassword(password)) {
            System.out.println("Error: Invalid password.");
            return;
        }

        if (!isValidPhoneNumber(phone)) {
            System.out.println("Error: Invalid phone number.");
            return;
        }

        if (!isValidEmail(email)) {
            System.out.println("Error: Invalid email.");
            return;
        }

        String card = generateCardNumber();
        User user = new User(username, password, fullName, card, phone, email);

        usersByUsername.put(username, user);
        usersByCardNumber.put(card, user);

        System.out.println("Registered successfully.");
        System.out.println("Assigned card number: " + card);
    }

    public void login(String username, String password) {
        User user = usersByUsername.get(username);

        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("Error: Invalid username or password.");
            return;
        }

        currentUser = user;
        System.out.println("Login successful.");
    }

    public void showBalance() {
        if (!isLoggedIn()) return;
        System.out.println("Current balance: " + currentUser.getBalance());
    }

    public void deposit(double amount) {
        if (!isLoggedIn()) return;
        currentUser.deposit(amount);
        System.out.println("Deposit successful. Current balance: " + currentUser.getBalance());
    }

    public void withdraw(double amount) {
        if (!isLoggedIn()) return;
        if (!currentUser.withdraw(amount)) {
            System.out.println("Error: insufficient balance.");
            return;
        }
        System.out.println("Withdrawal successful. Current balance: " + currentUser.getBalance());
    }

    public void transfer(String card, double amount) {
        if (!isLoggedIn()) return;

        User receiver = usersByCardNumber.get(card);

        if (receiver == null) {
            System.out.println("Error: invalid card number.");
            return;
        }

        if (!currentUser.withdraw(amount)) {
            System.out.println("Error: insufficient balance.");
            return;
        }

        receiver.deposit(amount);
        System.out.println("Transferred successfully.");
    }

    public void logout() {
        if (currentUser == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }
        currentUser = null;
        System.out.println("Logout successful.");
    }

    private boolean isLoggedIn() {
        if (currentUser == null) {
            System.out.println("Error: You should login first.");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String pass) {
        if (pass.length() < 8) return false;
        boolean upper = false, lower = false, digit = false, special = false;
        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) upper = true;
            else if (Character.isLowerCase(c)) lower = true;
            else if (Character.isDigit(c)) digit = true;
            else if ("@!&$؟".indexOf(c) != -1) special = true;
        }
        return upper && lower && digit && special;
    }

    private boolean isValidPhoneNumber(String phone) {
        if (phone.length() != 11) return false;
        if (!phone.startsWith("09")) return false;
        for (char c : phone.toCharArray()) if (!Character.isDigit(c)) return false;
        return true;
    }

    private boolean isValidEmail(String email) {
        int at = email.indexOf("@");
        if (at <= 0) return false;
        if (!email.endsWith("aut.com")) return false;
        return true;
    }

    private String generateCardNumber() {
        String card;
        do {
            StringBuilder sb = new StringBuilder("6037");
            for (int i = 0; i < 12; i++) sb.append((int) (Math.random() * 10));
            card = sb.toString();
        } while (usersByCardNumber.containsKey(card));
        return card;
    }
}
