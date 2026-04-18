package com.periodTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cycles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;
    private int cycleLength;         // actual (can differ from profile)
    private int periodDuration;      // actual
    // Calculated fields (optional but useful)
    private LocalDate ovulationDate;
    private LocalDate nextPeriodDate;

    private LocalDate createdAt;
}
