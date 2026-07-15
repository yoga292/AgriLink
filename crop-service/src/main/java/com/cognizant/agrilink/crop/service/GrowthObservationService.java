package com.cognizant.agrilink.crop.service;

import com.cognizant.agrilink.crop.dto.GrowthObservationDto;
import com.cognizant.agrilink.crop.entity.GrowthObservation;
import com.cognizant.agrilink.crop.repository.GrowthObservationRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GrowthObservationService {

	private final GrowthObservationRepository growthObservationRepository;

	public GrowthObservationService(GrowthObservationRepository growthObservationRepository) {
		this.growthObservationRepository = growthObservationRepository;
	}

	public List<GrowthObservation> getAll() {
		return growthObservationRepository.findAll();
	}

	public GrowthObservation getById(Integer id) {
		return growthObservationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("GrowthObservation not found with id " + id));
	}

	public GrowthObservation create(GrowthObservationDto dto) {
		GrowthObservation growthObservation = GrowthObservation.builder()
				.planId(dto.getPlanId())
				.officerId(dto.getOfficerId())
				.observationDate(dto.getObservationDate())
				.stage(dto.getStage())
				.pestOrDiseaseFlag(dto.getPestOrDiseaseFlag())
				.remarks(dto.getRemarks())
				.build();
		return growthObservationRepository.save(growthObservation);
	}

	public GrowthObservation update(Integer id, GrowthObservationDto dto) {
		GrowthObservation growthObservation = getById(id);
		growthObservation.setPlanId(dto.getPlanId());
		growthObservation.setOfficerId(dto.getOfficerId());
		growthObservation.setObservationDate(dto.getObservationDate());
		growthObservation.setStage(dto.getStage());
		growthObservation.setPestOrDiseaseFlag(dto.getPestOrDiseaseFlag());
		growthObservation.setRemarks(dto.getRemarks());
		return growthObservationRepository.save(growthObservation);
	}

	public void delete(Integer id) {
		GrowthObservation growthObservation = getById(id);
		growthObservationRepository.delete(growthObservation);
	}
}
