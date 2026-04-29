package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_limits")
public class UserLimit {
    @Id
    private Long id;

    @Column(nullable = false)
    private BigDecimal availableBalance = new BigDecimal("100000.00");

    @Column(nullable = false)
    private BigDecimal reservedSum = BigDecimal.ZERO;

}