package com.cognizant.agrilink.farmer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cognizant.agrilink.farmer.entity.LandHolding;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class LandHoldingRepositoryTest {

	@Autowired
	private LandHoldingRepository landHoldingRepository;

	private LandHolding buildLandHolding() {
		return LandHolding.builder()
				.farmerId(1)
				.surveyNumber("SY-101/2A")
				.areaAcres(5.5)
				.soilType("Black")
				.irrigationSource("Borewell")
				.ownershipType("Owned")
				.status("Active")
				.build();
	}

	@Test
	void saveAndFindById() {
		LandHolding saved = landHoldingRepository.save(buildLandHolding());

		LandHolding found = landHoldingRepository.findById(saved.getHoldingId()).orElseThrow();

		assertThat(found.getSurveyNumber()).isEqualTo("SY-101/2A");
		assertThat(found.getSoilType()).isEqualTo("Black");
	}

	@Test
	void findAllReturnsSavedRecords() {
		landHoldingRepository.save(buildLandHolding());
		landHoldingRepository.save(buildLandHolding());

		assertThat(landHoldingRepository.findAll()).hasSize(2);
	}

	@Test
	void deleteRemovesRecord() {
		LandHolding saved = landHoldingRepository.save(buildLandHolding());

		landHoldingRepository.deleteById(saved.getHoldingId());

		assertThat(landHoldingRepository.findById(saved.getHoldingId())).isEmpty();
	}
}
