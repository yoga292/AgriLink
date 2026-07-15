package com.cognizant.agrilink.farmer.service;

import com.cognizant.agrilink.farmer.dto.LandHoldingDto;
import com.cognizant.agrilink.farmer.entity.LandHolding;
import com.cognizant.agrilink.farmer.repository.LandHoldingRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LandHoldingService {

	private final LandHoldingRepository landHoldingRepository;

	public LandHoldingService(LandHoldingRepository landHoldingRepository) {
		this.landHoldingRepository = landHoldingRepository;
	}

	public List<LandHolding> getAll() {
		return landHoldingRepository.findAll();
	}

	public List<LandHolding> getByFarmerIds(List<Integer> farmerIds) {
		if (farmerIds == null || farmerIds.isEmpty()) {
			return List.of();
		}
		return landHoldingRepository.findByFarmerIdIn(farmerIds);
	}

	public LandHolding getById(Integer id) {
		return landHoldingRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("LandHolding not found with id " + id));
	}

	public LandHolding create(LandHoldingDto dto) {
		LandHolding landHolding = LandHolding.builder()
				.farmerId(dto.getFarmerId())
				.surveyNumber(dto.getSurveyNumber())
				.areaAcres(dto.getAreaAcres())
				.soilType(dto.getSoilType())
				.irrigationSource(dto.getIrrigationSource())
				.ownershipType(dto.getOwnershipType())
				.status(dto.getStatus())
				.build();
		return landHoldingRepository.save(landHolding);
	}

	public LandHolding update(Integer id, LandHoldingDto dto) {
		LandHolding landHolding = getById(id);
		landHolding.setFarmerId(dto.getFarmerId());
		landHolding.setSurveyNumber(dto.getSurveyNumber());
		landHolding.setAreaAcres(dto.getAreaAcres());
		landHolding.setSoilType(dto.getSoilType());
		landHolding.setIrrigationSource(dto.getIrrigationSource());
		landHolding.setOwnershipType(dto.getOwnershipType());
		landHolding.setStatus(dto.getStatus());
		return landHoldingRepository.save(landHolding);
	}

	public void delete(Integer id) {
		LandHolding landHolding = getById(id);
		landHoldingRepository.delete(landHolding);
	}
}
