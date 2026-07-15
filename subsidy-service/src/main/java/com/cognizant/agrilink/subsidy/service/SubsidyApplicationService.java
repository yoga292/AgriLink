package com.cognizant.agrilink.subsidy.service;

import com.cognizant.agrilink.subsidy.dto.SubsidyApplicationDto;
import com.cognizant.agrilink.subsidy.entity.SubsidyApplication;
import com.cognizant.agrilink.subsidy.repository.SubsidyApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SubsidyApplicationService {

	private final SubsidyApplicationRepository subsidyApplicationRepository;

	public SubsidyApplicationService(SubsidyApplicationRepository subsidyApplicationRepository) {
		this.subsidyApplicationRepository = subsidyApplicationRepository;
	}

	public List<SubsidyApplication> getAll() {
		return subsidyApplicationRepository.findAll();
	}

	public List<SubsidyApplication> getByUserId(Integer userId) {
		return subsidyApplicationRepository.findByUserId(userId);
	}

	public SubsidyApplication getById(Integer id) {
		return subsidyApplicationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("SubsidyApplication not found with id " + id));
	}

	public SubsidyApplication create(SubsidyApplicationDto dto) {
		SubsidyApplication subsidyApplication = SubsidyApplication.builder()
				.farmerId(dto.getFarmerId())
				.userId(dto.getUserId())
				.schemeId(dto.getSchemeId())
				.applicationDate(dto.getApplicationDate())
				.eligibilityScore(dto.getEligibilityScore())
				.reviewedBy(dto.getReviewedBy())
				.disbursedAmount(dto.getDisbursedAmount())
				.disbursedDate(dto.getDisbursedDate())
				.status(dto.getStatus())
				.build();
		return subsidyApplicationRepository.save(subsidyApplication);
	}

	public SubsidyApplication update(Integer id, SubsidyApplicationDto dto) {
		SubsidyApplication subsidyApplication = getById(id);
		subsidyApplication.setFarmerId(dto.getFarmerId());
		subsidyApplication.setUserId(dto.getUserId());
		subsidyApplication.setSchemeId(dto.getSchemeId());
		subsidyApplication.setApplicationDate(dto.getApplicationDate());
		subsidyApplication.setEligibilityScore(dto.getEligibilityScore());
		subsidyApplication.setReviewedBy(dto.getReviewedBy());
		subsidyApplication.setDisbursedAmount(dto.getDisbursedAmount());
		subsidyApplication.setDisbursedDate(dto.getDisbursedDate());
		subsidyApplication.setStatus(dto.getStatus());
		return subsidyApplicationRepository.save(subsidyApplication);
	}

	public void delete(Integer id) {
		SubsidyApplication subsidyApplication = getById(id);
		subsidyApplicationRepository.delete(subsidyApplication);
	}
}
