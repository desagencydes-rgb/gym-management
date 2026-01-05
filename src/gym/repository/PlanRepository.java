package gym.repository;

import gym.model.MembershipPlan;

public class PlanRepository extends FileRepository<MembershipPlan> {
    public PlanRepository() {
        super("data/plans.dat");
    }

    @Override
    protected String getId(MembershipPlan item) {
        return item.getId();
    }
}
