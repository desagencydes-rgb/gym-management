package gym.repository;

import gym.model.Subscription;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionRepository extends FileRepository<Subscription> {
    public SubscriptionRepository() {
        super("data/subscriptions.dat");
    }

    @Override
    protected String getId(Subscription item) {
        return item.getId();
    }

    public List<Subscription> findByMemberId(String memberId) {
        return getAll().stream()
                .filter(s -> s.getMemberId().equals(memberId))
                .collect(Collectors.toList());
    }
}
