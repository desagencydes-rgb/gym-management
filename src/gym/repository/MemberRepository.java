package gym.repository;

import gym.model.Member;

public class MemberRepository extends FileRepository<Member> {
    public MemberRepository() {
        super("data/members.dat");
    }

    @Override
    protected String getId(Member item) {
        return item.getId();
    }
}
