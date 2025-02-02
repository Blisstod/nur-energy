package kz.nur.energy.repository;

import kz.nur.energy.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BalanceRepository extends JpaRepository<Balance, UUID> {
}
