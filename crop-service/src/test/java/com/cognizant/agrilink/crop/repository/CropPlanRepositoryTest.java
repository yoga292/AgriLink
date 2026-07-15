package com.cognizant.agrilink.crop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cognizant.agrilink.crop.entity.CropPlan;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class CropPlanRepositoryTest {

	@Autowired
	private CropPlanRepository cropPlanRepository;

	private CropPlan buildCropPlan() {
		return CropPlan.builder()
				.farmerId(1)
				.holdingId(2)
				.cropId(3)
				.season("Rabi")
				.year(2026)
				.sowingDate(LocalDate.of(2026, 6, 15))
				.expectedHarvestDate(LocalDate.of(2026, 10, 15))
				.areaPlanted(5.5)
				.status("Planned")
				.build();
	}

	@Test
	void saveAndFindById() {
		CropPlan saved = cropPlanRepository.save(buildCropPlan());

		CropPlan found = cropPlanRepository.findById(saved.getPlanId()).orElseThrow();

		assertThat(found.getSeason()).isEqualTo("Rabi");
		assertThat(found.getStatus()).isEqualTo("Planned");
	}

	@Test
	void findAllReturnsSavedRecords() {
		cropPlanRepository.save(buildCropPlan());
		cropPlanRepository.save(buildCropPlan());

		assertThat(cropPlanRepository.findAll()).hasSize(2);
	}

	@Test
	void deleteRemovesRecord() {
		CropPlan saved = cropPlanRepository.save(buildCropPlan());

		cropPlanRepository.deleteById(saved.getPlanId());

		assertThat(cropPlanRepository.findById(saved.getPlanId())).isEmpty();
	}
}
