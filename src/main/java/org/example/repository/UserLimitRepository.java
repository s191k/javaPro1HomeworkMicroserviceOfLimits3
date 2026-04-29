package org.example.repository;

import org.example.entities.UserLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
public interface UserLimitRepository extends JpaRepository<UserLimit, Long> {

    @Modifying
    @Query("UPDATE UserLimit u SET u.availableBalance = :defaultLimit, u.reservedSum = 0")
    int resetAllLimits(@Param("defaultLimit") BigDecimal defaultLimit);

    @Modifying
    @Query("UPDATE UserLimit u SET u.availableBalance = u.availableBalance - :amount, " +
            "u.reservedSum = u.reservedSum + :amount " +
            "WHERE u.id = :id AND u.availableBalance >= :amount")
    int reserve(@Param("id") Long id, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("UPDATE UserLimit u SET u.reservedSum = u.reservedSum - :amount WHERE u.id = :id")
    int confirm(@Param("id") Long id, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("UPDATE UserLimit u SET u.availableBalance = u.availableBalance + :amount, " +
            "u.reservedSum = u.reservedSum - :amount WHERE u.id = :id")
    int cancel(@Param("id") Long id, @Param("amount") BigDecimal amount);

}
