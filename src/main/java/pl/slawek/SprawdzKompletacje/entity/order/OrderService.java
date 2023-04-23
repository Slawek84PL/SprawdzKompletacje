package pl.slawek.SprawdzKompletacje.entity.order;

import org.springframework.stereotype.Service;

@Service
class OrderService {
    private final OrderRepository orderRepo;

    OrderService(final OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    private Long addOrder(String fileName) {
        Order order = new Order();
        order.setFileName(fileName);
        orderRepo.save(order);
        return order.getId();
    }
}
