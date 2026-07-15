package com.cognizant.agrilink.subsidy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cognizant.agrilink.subsidy.entity.SubsidyApplication;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class SubsidyApplicationRepositoryTest {

	@Autowired
	private SubsidyApplicationRepository subsidyApplicationRepository;

	private SubsidyApplication buildSubsidyApplication() {
		return SubsidyApplication.builder()
				.farmerId(1)
				.schemeId(1)
				.applicationDate(LocalDate.of(2026, 2, 1))
				.eligibilityScore(85.5)
				.reviewedBy(2)
				.disbursedAmount(6000.0)
				.disbursedDate(LocalDate.of(2026, 3, 1))
				.status("Approved")
				.build();
	}

	@Test
	void saveAndFindById() {
		SubsidyApplication saved = subsidyApplicationRepository.save(buildSubsidyApplication());

		SubsidyApplication found = subsidyApplicationRepository.findById(saved.getApplicationId()).orElseThrow();

		assertThat(found.getStatus()).isEqualTo("Approved");
		assertThat(found.getFarmerId()).isEqualTo(1);
	}

	@Test
	void findAllReturnsSavedRecords() {
		subsidyApplicationRepository.save(buildSubsidyApplication());
		subsidyApplicationRepository.save(buildSubsidyApplication());

		assertThat(subsidyApplicationRepository.findAll()).hasSize(2);
	}

	@Test
	void deleteRemovesRecord() {
		SubsidyApplication saved = subsidyApplicationRepository.save(buildSubsidyApplication());

		subsidyApplicationRepository.deleteById(saved.getApplicationId());

		assertThat(subsidyApplicationRepository.findById(saved.getApplicationId())).isEmpty();
	}
}
