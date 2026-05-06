package org.example.repository;

import org.example.entities.LimitReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface LimitReservationRepository extends JpaRepository<LimitReservation, UUID> {

    Optional<LimitReservation> findByOperationId(UUID operationId);

}