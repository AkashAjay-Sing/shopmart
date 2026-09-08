package shopmart;

import java.time.LocalDateTime;

public class OrderItem {
    private final LocalDateTime timestamp;
    private final String type;
    private final double amount;
    private final String description;

    public OrderItem(LocalDateTime timestamp, String type, double amount, String description) {
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
