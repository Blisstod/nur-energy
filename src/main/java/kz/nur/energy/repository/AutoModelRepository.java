package kz.nur.energy.repository;

import kz.nur.energy.entity.AutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutoModelRepository extends JpaRepository<AutoModel, UUID> {
}
