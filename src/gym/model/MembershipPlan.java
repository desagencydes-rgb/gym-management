package gym.model;

import gym.model.enums.PlanDuration;
import java.io.Serializable;

public class MembershipPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private double price;
    private PlanDuration duration;

    public MembershipPlan(String id, String name, double price, PlanDuration duration) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PlanDuration getDuration() {
        return duration;
    }

    public void setDuration(PlanDuration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return name + " (" + duration + ")";
    }
}
