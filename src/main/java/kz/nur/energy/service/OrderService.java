package kz.nur.energy.service;

import jakarta.persistence.EntityNotFoundException;
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

import java.math.BigDecimal;
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

    @Autowired
    private ControlPointService controlPointService;

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private BalanceService balanceService;

    @Transactional
    public OrderResponse create(OrderRequest request) {
        Order order = new Order();
        ControlPoint startPoint = controlPointService.create(request.getStartPoint());
        ControlPoint destinationPoint = controlPointService.create(request.getDestinationPoint());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setPickUpTime(getPickUpTime(request.getPickUpTime()));
        order.setUser(SecurityUtils.getCurrentUser());
        order.setStatus(OrderStatus.NEW);
        order.setStartPoint(startPoint);
        order.setStartAddress(startPoint.getAddress());
        order.setDestinationPoint(destinationPoint);
        order.setDestinationAddress(destinationPoint.getAddress());

        double distance = distanceService.calculateDistance(request.getStartPoint(), request.getDestinationPoint());
        int price = distanceService.calculatePrice(distance);

        order.setDistance(distance);
        order.setPrice(BigDecimal.valueOf(price));

        orderRepository.save(order);
        return OrderResponse.of(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return OrderResponse.of(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getActiveOrders() {
        List<OrderStatus> activeStatuses = List.of(OrderStatus.NEW, OrderStatus.ACCEPTED, OrderStatus.IN_PROGRESS, OrderStatus.CANCELLED);
        return orderRepository.findOrdersByStatus(activeStatuses)
                .stream()
                .map(OrderResponse::of)
                .toList();
    }

    @Transactional
    public OrderResponse cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
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
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        User driver = SecurityUtils.getCurrentUser();
        order.setStatus(OrderStatus.ACCEPTED);
        order.setDriver(driver);
//        order.setCar(driver.getAutoList().get(0));
        orderRepository.save(order);
        return OrderResponse.of(order);
    }

    @Transactional
    public List<OrderResponse> getVacantOrders(){
        List<OrderStatus> activeStatuses = List.of(OrderStatus.NEW);
        return orderRepository.findOrdersByStatus(activeStatuses)
                .stream()
                .map(OrderResponse::of)
                .toList();
    }

    @Transactional
    public List<OrderResponse> getOrders(){
        User user = SecurityUtils.getCurrentUser();
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream().map(OrderResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getBookedOrders() {
        User driver = SecurityUtils.getCurrentUser();
        return orderRepository.findByDriverAndStatus(driver, OrderStatus.ACCEPTED)
                .stream().map(OrderResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getFinishedOrders() {
        User driver = SecurityUtils.getCurrentUser();
        return orderRepository.findByDriverAndStatus(driver, OrderStatus.FINISHED)
                .stream().map(OrderResponse::of).toList();
    }

    @Transactional
    public String finishOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(OrderStatus.FINISHED);

        balanceService.taxiPayment(order);

        orderRepository.save(order);
        return "Заказ закончен!";
    }

    @Transactional
    public String deleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(OrderStatus.FINISHED);

        orderRepository.delete(order);
        return "Order has been deleted!";
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
