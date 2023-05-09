package pl.slawek.SprawdzKompletacje.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderNumber, Long> {

    Optional<OrderNumber> findByFileName(String fileName);

    List<OrderNumber> findAll();

    List<OrderNumber> findByIsFinishedFalse();

    List<OrderNumber> findByIsFinishedTrueAndImportDateBetween(LocalDateTime importDateStart, LocalDateTime importDateEnd);



}