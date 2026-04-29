package org.example.repository;

import org.example.entities.LimitReservation;
import org.example.entities.UserLimit;
import org.example.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface LimitReservationRepository extends JpaRepository<LimitReservation, UUID> {

    Optional<LimitReservation> findByOperationId(UUID operationId);

}