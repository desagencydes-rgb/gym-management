package gym.model;

import gym.model.enums.PaymentMethod;
import java.io.Serializable;
import java.time.LocalDate;

public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String subscriptionId;
    private double amount;
    private LocalDate date;
    private PaymentMethod method;

    public Payment(String id, String subscriptionId, double amount, LocalDate date, PaymentMethod method) {
        this.id = id;
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.date = date;
        this.method = method;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
}
