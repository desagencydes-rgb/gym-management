package gym.model;

import gym.model.enums.Gender;
import java.time.LocalDate;

public class Member extends Person {
    private static final long serialVersionUID = 1L;

    private LocalDate joinDate;
    private boolean isActive;

    public Member(String id, String name, Gender gender, String phone, String email, LocalDate joinDate) {
        super(id, name, gender, phone, email);
        this.joinDate = joinDate;
        this.isActive = true;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
