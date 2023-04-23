package pl.slawek.SprawdzKompletacje.entity.order;

import org.springframework.stereotype.Service;

@Service
public
class OrderService {
    private final OrderRepository orderRepo;

    OrderService(final OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public long addOrder(String fileName) {
        OrderNumber orderNumber = new OrderNumber();
        orderNumber.setFileName(fileName);
        orderRepo.save(orderNumber);
        return orderNumber.getId();
    }
}
