package gym.repository;

import gym.model.Payment;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentRepository extends FileRepository<Payment> {
    public PaymentRepository() {
        super("data/payments.dat");
    }

    @Override
    protected String getId(Payment item) {
        return item.getId();
    }

    public List<Payment> findBySubscriptionId(String subId) {
        return getAll().stream()
                .filter(p -> p.getSubscriptionId().equals(subId))
                .collect(Collectors.toList());
    }
}
