package kz.nur.energy.repository;

import kz.nur.energy.entity.AutoBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutoBrandRepository extends JpaRepository<AutoBrand, UUID> {
}
