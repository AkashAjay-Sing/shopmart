package shopmart;

import java.util.ArrayList;

public class Customer {
    private final Integer id;
    private final String customerName;
    private double walletBalance;
    private final ArrayList<OrderItem> orders;

    public Customer(Integer id, String customerName, double walletBalance) {
        this.id = id;
        this.customerName = customerName;
        this.walletBalance = walletBalance;
        this.orders = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public ArrayList<OrderItem> getOrders() {
        return orders;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }
}
