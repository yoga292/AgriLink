package com.cognizant.agrilink.subsidy.service;

import com.cognizant.agrilink.subsidy.dto.SchemeCatalogDto;
import com.cognizant.agrilink.subsidy.entity.SchemeCatalog;
import com.cognizant.agrilink.subsidy.repository.SchemeCatalogRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SchemeCatalogService {

	private final SchemeCatalogRepository schemeCatalogRepository;

	public SchemeCatalogService(SchemeCatalogRepository schemeCatalogRepository) {
		this.schemeCatalogRepository = schemeCatalogRepository;
	}

	public List<SchemeCatalog> getAll() {
		return schemeCatalogRepository.findAll();
	}

	public SchemeCatalog getById(Integer id) {
		return schemeCatalogRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("SchemeCatalog not found with id " + id));
	}

	public SchemeCatalog create(SchemeCatalogDto dto) {
		SchemeCatalog schemeCatalog = SchemeCatalog.builder()
				.schemeName(dto.getSchemeName())
				.category(dto.getCategory())
				.eligibilityCriteria(dto.getEligibilityCriteria())
				.benefitAmount(dto.getBenefitAmount())
				.fundingSource(dto.getFundingSource())
				.startDate(dto.getStartDate())
				.endDate(dto.getEndDate())
				.status(dto.getStatus())
				.build();
		return schemeCatalogRepository.save(schemeCatalog);
	}

	public SchemeCatalog update(Integer id, SchemeCatalogDto dto) {
		SchemeCatalog schemeCatalog = getById(id);
		schemeCatalog.setSchemeName(dto.getSchemeName());
		schemeCatalog.setCategory(dto.getCategory());
		schemeCatalog.setEligibilityCriteria(dto.getEligibilityCriteria());
		schemeCatalog.setBenefitAmount(dto.getBenefitAmount());
		schemeCatalog.setFundingSource(dto.getFundingSource());
		schemeCatalog.setStartDate(dto.getStartDate());
		schemeCatalog.setEndDate(dto.getEndDate());
		schemeCatalog.setStatus(dto.getStatus());
		return schemeCatalogRepository.save(schemeCatalog);
	}

	public void delete(Integer id) {
		SchemeCatalog schemeCatalog = getById(id);
		schemeCatalogRepository.delete(schemeCatalog);
	}
}
