package kz.nur.energy.service;

import kz.nur.energy.dto.OrderRequest;
import kz.nur.energy.dto.OrderResponse;
import kz.nur.energy.entity.ControlPoint;
import kz.nur.energy.entity.Order;
import kz.nur.energy.entity.User;
import kz.nur.energy.enums.OrderStatus;
import kz.nur.energy.repository.ControlPointRepository;
import kz.nur.energy.repository.OrderRepository;
import kz.nur.energy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderService {

    private static final DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ControlPointRepository controlPointRepository;

    @Transactional
    public OrderResponse create(OrderRequest request) {
        Order order = new Order();
        order.setPhoneNumber(request.getPhoneNumber());
        order.setPrice(request.getPrice());
        order.setPickUpTime(getPickUpTime(request.getPickUpTime()));
        order.setStartPoint(getControlPoint(request.getStartPoint().getId()));
        order.setDestinationPoint(getControlPoint(request.getDestinationPoint().getId()));
        order.setUser(SecurityUtils.getCurrentUser());
        order.setStatus(OrderStatus.NEW);

        orderRepository.save(order);
        return OrderResponse.of(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderResponse.of(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getActiveOrders() {
        List<OrderStatus> activeStatuses = List.of(OrderStatus.NEW, OrderStatus.ACCEPTED, OrderStatus.IN_PROGRESS, OrderStatus.CANCELLED);
        return orderRepository.findActiveOrders(activeStatuses)
                .stream()
                .map(OrderResponse::of)
                .toList();
    }

    @Transactional
    public OrderResponse cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
//        order.setCar(null);
        order.setDriver(null);
        order.setDriver(null);
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return OrderResponse.of(order);
    }

    @Transactional
    public OrderResponse bookOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        User driver = SecurityUtils.getCurrentUser();
        order.setStatus(OrderStatus.ACCEPTED);
        order.setDriver(driver);
//        order.setCar(driver.getAutoList().get(0));
        orderRepository.save(order);
        return OrderResponse.of(order);
    }

    public void notifyDrivers(OrderResponse response) {
        // Логика отправки уведомлений водителям
    }

    private LocalDateTime getPickUpTime(String pickupTime) {
        try {
            LocalDateTime pickUptime = LocalDateTime.parse(pickupTime, myFormat);
            if (pickUptime.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("PickUpTime must be after current time");
            }
            return pickUptime;
        } catch (Exception exception) {
            throw new IllegalArgumentException("Wrong pickUpTime format");
        }
    }

    private ControlPoint getControlPoint(UUID controlPointId) {
        return controlPointRepository.findById(controlPointId).orElseThrow(() -> new NoSuchElementException("ControlPoint not found"));
    }
}
