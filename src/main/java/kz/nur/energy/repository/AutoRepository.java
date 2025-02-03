package kz.nur.energy.repository;

import kz.nur.energy.entity.Auto;
import kz.nur.energy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, UUID> {
    List<Auto> findByUser(User user);
}
