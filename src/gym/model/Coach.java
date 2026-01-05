package gym.model;

import gym.model.enums.Gender;

public class Coach extends Person {
    private static final long serialVersionUID = 1L;

    private String specialization;

    public Coach(String id, String name, Gender gender, String phone, String email, String specialization) {
        super(id, name, gender, phone, email);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
