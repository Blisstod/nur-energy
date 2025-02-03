package kz.nur.energy.repository;

import kz.nur.energy.entity.Order;
import kz.nur.energy.entity.User;
import kz.nur.energy.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM order o WHERE o.status IN (:statuses) AND o.deletedDate IS NULL")
    List<Order> findOrdersByStatus(List<OrderStatus> statuses);

    List<Order> findByUser(User user);

    List<Order> findByDriverAndStatus(User driver, OrderStatus status);
}
