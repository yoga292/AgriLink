package com.cognizant.agrilink.crop.repository;

import com.cognizant.agrilink.crop.entity.CropPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropPlanRepository extends JpaRepository<CropPlan, Integer> {
}
