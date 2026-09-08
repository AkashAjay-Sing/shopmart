package shopmart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ShopMart {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static void main(String[] args) {
        StoreLedger storeLedger = new StoreLedger();

        while (true) {
            System.out.println();
            System.out.println("============================================================");
            System.out.println("                  SHOPMART — CONSOLE MENU");
            System.out.println("============================================================");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Purchase (Order)");
            System.out.println("3. Process Refund");
            System.out.println("4. Display Order History");
            System.out.println("5. Exit");
            System.out.println("============================================================");
            System.out.print("Select Option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addCustomer(storeLedger);
                case 2 -> addPurchase(storeLedger);
                case 3 -> processRefund(storeLedger);
                case 4 -> displayOrderHistory(storeLedger);
                case 5 -> {
                    System.out.println("Exiting SHOPMART. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("[ERROR] Invalid option.");
            }
        }
    }

    private static void addCustomer(StoreLedger storeLedger) {
        System.out.print("Enter Customer ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Initial Wallet Balance: ");
        double balance = Double.parseDouble(scanner.nextLine());

        storeLedger.addCustomer(id, name, balance);
    }

    private static void addPurchase(StoreLedger storeLedger) {
        System.out.print("Enter Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter Date-Time (YYYY-MM-DDTHH:MM:SS): ");
        LocalDateTime time = LocalDateTime.parse(scanner.nextLine());

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        storeLedger.addPurchase(customerId, amount, time, description);
    }

    private static void processRefund(StoreLedger storeLedger) {
        System.out.print("Enter Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter Date-Time (YYYY-MM-DDTHH:MM:SS): ");
        LocalDateTime time = LocalDateTime.parse(scanner.nextLine());

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        storeLedger.processRefund(customerId, amount, time, description);
    }

    private static void displayOrderHistory(StoreLedger storeLedger) {
        System.out.print("Enter Customer ID: ");
        int customerId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Start Date-Time: ");
        LocalDateTime startDate = LocalDateTime.parse(scanner.nextLine());

        System.out.print("Enter End Date-Time: ");
        LocalDateTime endDate = LocalDateTime.parse(scanner.nextLine());

        Customer customer = storeLedger.getCustomer(customerId);

        if (customer == null) {
            System.out.println("[ERROR] Customer not found.");
            return;
        }

        ArrayList<OrderItem> history = storeLedger.getOrderHistory(customerId, startDate, endDate);

        System.out.println();
        System.out.println("============================================================");
        System.out.println("        ORDER HISTORY: " + customerId + " (" + customer.getCustomerName() + ")");
        System.out.println("        Filter Period: " + startDate.toLocalDate() + " to " + endDate.toLocalDate());
        System.out.println("============================================================");
        System.out.println("DATE & TIME       | TYPE     | AMOUNT     | DESCRIPTION");
        System.out.println("------------------------------------------------------------");

        for (OrderItem order : history) {
            String sign = order.getType().equals("REFUND") ? "+" : "-";
            System.out.printf("%s | %-8s | %s₹%,.2f | %s%n",
                    order.getTimestamp().format(DISPLAY_FORMAT),
                    order.getType(),
                    sign,
                    Math.abs(order.getAmount()),
                    order.getDescription());
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("History complete (" + history.size() + " order(s) found in date range)");
    }
}
