import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankSystem bank = new BankSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;
            String[] parts = input.split(" ");

            switch (parts[0]) {
                case "register":
                    if (parts.length < 6) { System.out.println("Error: Missing arguments."); break; }
                    bank.register(parts[1], parts[2], parts[3], parts[4], parts[5]);
                    break;

                case "login":
                    if (parts.length < 3) { System.out.println("Error: Missing arguments."); break; }
                    bank.login(parts[1], parts[2]);
                    break;

                case "show":
                    if (parts.length == 2 && parts[1].equals("balance"))
                        bank.showBalance();
                    else
                        System.out.println("Error: Unknown command.");
                    break;

                case "deposit":
                    if (parts.length < 2) { System.out.println("Error: Missing amount."); break; }
                    try { bank.deposit(Double.parseDouble(parts[1])); }
                    catch (Exception e) { System.out.println("Error: Invalid amount."); }
                    break;

                case "withdraw":
                    if (parts.length < 2) { System.out.println("Error: Missing amount."); break; }
                    try { bank.withdraw(Double.parseDouble(parts[1])); }
                    catch (Exception e) { System.out.println("Error: Invalid amount."); }
                    break;

                case "transfer":
                    if (parts.length < 3) { System.out.println("Error: Missing arguments."); break; }
                    try { bank.transfer(parts[1], Double.parseDouble(parts[2])); }
                    catch (Exception e) { System.out.println("Error: Invalid amount."); }
                    break;

                case "logout":
                    bank.logout();
                    break;

                case "exit":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Error: Unknown command.");
            }
        }
    }
}
