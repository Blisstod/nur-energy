package kz.nur.energy.repository;

import kz.nur.energy.entity.ControlPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ControlPointRepository extends JpaRepository<ControlPoint, UUID> {
}
