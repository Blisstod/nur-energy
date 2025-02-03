package kz.nur.energy.service;

import kz.nur.energy.dto.BalanceInfo;
import kz.nur.energy.entity.Balance;
import kz.nur.energy.entity.Order;
import kz.nur.energy.entity.User;
import kz.nur.energy.repository.BalanceRepository;
import kz.nur.energy.repository.OrderRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.*;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public BalanceInfo getBalanceInfo(User user){
        Balance balance = getBalance(user);

        return BalanceInfo.builder()
                .phoneNum(user.getMobileNum())
                .balanceId(balance.getId())
                .balance(balance.getBalance())
                .build();
    }

    @Transactional
    public String accrualBalance(User user, Long refill){
        Balance balance = getBalance(user);
        balance.setBalance(balance.getBalance() + refill);
        balanceRepository.save(balance);
        return "Баланс был пополнен";
    }

    @Transactional
    public void taxiPayment(Order order){
        if (order.getDriver() == null || order.getPrice() == null) {
            throw new IllegalStateException("Нельзя выполнить оплату: у заказа нет водителя или цена не задана");
        }

        User passenger = order.getUser();
        User driver = order.getDriver();
        Long price = order.getPrice().longValue();

        Balance passengerBalance = passenger.getBalance();
        Balance driverBalance = driver.getBalance();

        if (price > passenger.getBalance().getBalance()){
            throw new IllegalStateException("Недостаточна средств на балансе");
        }

        passengerBalance.setBalance(passengerBalance.getBalance() - price);

        driverBalance.setBalance(driverBalance.getBalance() + price);

        balanceRepository.save(passengerBalance);
        balanceRepository.save(driverBalance);
    }

    public Balance getBalance(User user){
        return balanceRepository.findById(user.getBalance().getId())
                .orElseThrow(() -> new RuntimeException("Balance not found"));
    }
}
