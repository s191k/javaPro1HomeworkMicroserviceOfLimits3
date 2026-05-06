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
    @Column(name = "id")
    private Long id;

    @Column(name = "available_balance", nullable = false)
    private BigDecimal availableBalance;

    @Column(name = "reserved_sum", nullable = false)
    private BigDecimal reservedSum;

}