package pl.slawek.SprawdzKompletacje.entity.order;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepo;

    OrderService(final OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<OrderNumber> findAllActiveOrders() {
        return orderRepo.findByIsFinishedFalse();
    }

    public void finishOrder(final String fileName) {
        OrderNumber orderNumber = orderRepo.findByFileName(fileName).get();
        orderNumber.setFinished(true);
        orderNumber.setFinishedDate(LocalDateTime.now());
        orderRepo.save(orderNumber);
    }

    public void save(final OrderNumber orderNumber) {
        orderRepo.save(orderNumber);
    }

    public List<OrderNumber> findByIsFinishedTrueAndImportDateBetween(LocalDate start, LocalDate finish) {
        LocalDateTime startLocal = LocalDateTime.of(
                start.getYear(),
                start.getMonth(),
                start.getDayOfMonth(),
                0,0,0);
        LocalDateTime finishLocal = LocalDateTime.of(
                finish.getYear(),
                finish.getMonth(),
                finish.getDayOfMonth()
                , 23,59,59, 99999999);
        return orderRepo.findByIsFinishedTrueAndImportDateBetween(startLocal, finishLocal);
    }
}
