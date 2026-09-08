package shopmart;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StoreLedger {
    private final ArrayList<Customer> customers;

    public StoreLedger() {
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Integer id, String name, double initialWalletBalance) {
        for (Customer cust : customers) {
            if (cust.getId().equals(id)) {
                System.out.println("[ERROR] Customer ID " + id + " already exists.");
                return;
            }
        }

        Customer newCustomer = new Customer(id, name, initialWalletBalance);
        customers.add(newCustomer);

        System.out.println("[SUCCESS] Customer " + id + " registered: " + name +
                " | Wallet Balance: ₹" + formatAmount(initialWalletBalance));
    }

    public void addPurchase(Integer customerId, double amount, LocalDateTime time, String description) {
        for (Customer cust : customers) {
            if (cust.getId().equals(customerId)) {
                cust.setWalletBalance(cust.getWalletBalance() - amount);

                OrderItem order = new OrderItem(time, "PURCHASE", -amount, description);
                cust.getOrders().add(order);

                System.out.println("[SUCCESS] Order recorded for Customer " + customerId +
                        ": -₹" + formatAmount(amount) +
                        " | Wallet Balance: ₹" + formatAmount(cust.getWalletBalance()));
                return;
            }
        }

        System.out.println("[ERROR] Customer ID " + customerId + " not found.");
    }
    
    public void processRefund(Integer customerId, double amount, LocalDateTime time, String description) {
        for (Customer cust : customers) {
            if (cust.getId().equals(customerId)) {
                if (amount <= 0) {
                    System.out.println("[ERROR] Invalid refund amount. Refund must be positive.");
                    return;
                }

                cust.setWalletBalance(cust.getWalletBalance() + amount);

                OrderItem refund = new OrderItem(time, "REFUND", amount, description);
                cust.getOrders().add(refund);

                System.out.println("[SUCCESS] Refund processed for Customer " + customerId +
                        ": +₹" + formatAmount(amount) +
                        " | Wallet Balance: ₹" + formatAmount(cust.getWalletBalance()));
                return;
            }
        }

        System.out.println("[ERROR] Customer ID " + customerId + " not found.");
    }

    public ArrayList<OrderItem> getOrderHistory(Integer customerId, LocalDateTime startDate, LocalDateTime endDate) {
        ArrayList<OrderItem> filteredOrders = new ArrayList<>();

        for (Customer cust : customers) {
            if (cust.getId().equals(customerId)) {
                for (OrderItem order : cust.getOrders()) {
                    if ((order.getTimestamp().isEqual(startDate) || order.getTimestamp().isAfter(startDate)) &&
                            (order.getTimestamp().isEqual(endDate) || order.getTimestamp().isBefore(endDate))) {
                        filteredOrders.add(order);
                    }
                }
                return filteredOrders;
            }
        }

        return filteredOrders;
    }

    public Customer getCustomer(Integer customerId) {
        for (Customer cust : customers) {
            if (cust.getId().equals(customerId)) {
                return cust;
            }
        }
        return null;
    }

    private String formatAmount(double amount) {
        return String.format("%,.2f", Math.abs(amount));
    }
}
