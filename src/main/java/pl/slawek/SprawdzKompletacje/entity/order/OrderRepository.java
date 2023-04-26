package pl.slawek.SprawdzKompletacje.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderNumber, Long> {
    @Transactional
    @Modifying
    @Query("update OrderNumber o set o.id = ?1")
    int updateIdBy(Long id);


    Optional<OrderNumber> findByFileName(String fileName);

    List<OrderNumber> findAll();

    List<OrderNumber> findByIsFinishedFalse();

}