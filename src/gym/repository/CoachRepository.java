package gym.repository;

import gym.model.Coach;

public class CoachRepository extends FileRepository<Coach> {
    public CoachRepository() {
        super("data/coaches.dat");
    }

    @Override
    protected String getId(Coach item) {
        return item.getId();
    }
}
