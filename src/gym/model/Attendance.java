package gym.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String memberId;
    private LocalDateTime timestamp;

    public Attendance(String id, String memberId, LocalDateTime timestamp) {
        this.id = id;
        this.memberId = memberId;
        this.timestamp = timestamp;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
