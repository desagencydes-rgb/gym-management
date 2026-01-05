package gym.repository;

import gym.model.Attendance;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceRepository extends FileRepository<Attendance> {
    public AttendanceRepository() {
        super("data/attendance.dat");
    }

    @Override
    protected String getId(Attendance item) {
        return item.getId();
    }

    public List<Attendance> findByMemberId(String memberId) {
        return getAll().stream()
                .filter(a -> a.getMemberId().equals(memberId))
                .collect(Collectors.toList());
    }
}
