package com.periodTracker.repository;

import com.periodTracker.entity.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CycleRepository extends JpaRepository<Cycle,Long> {
    List<Cycle> findByUserUserIdOrderByStartDateDesc(Long userId);

}
