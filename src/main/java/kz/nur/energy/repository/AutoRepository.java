package kz.nur.energy.repository;

import kz.nur.energy.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutoRepository extends JpaRepository<Auto, UUID> {
}
