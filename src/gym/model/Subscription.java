package gym.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Subscription implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String memberId;
    private String planId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;

    public Subscription(String id, String memberId, String planId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.memberId = memberId;
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = true;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
