package kz.nur.energy.service;

import kz.nur.energy.dto.BalanceInfo;
import kz.nur.energy.entity.Balance;
import kz.nur.energy.entity.User;
import kz.nur.energy.repository.BalanceRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

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

    public Balance getBalance(User user){

        return balanceRepository.findById(user.getBalance().getId())
                .orElseThrow(() -> new RuntimeException("Balance not found"));
    }
}
