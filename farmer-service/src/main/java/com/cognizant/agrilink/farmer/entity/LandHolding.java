package com.cognizant.agrilink.farmer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LandHolding")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LandHolding {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HoldingID")
	private Integer holdingId;

	@Column(name = "FarmerID")
	private Integer farmerId;

	@Column(name = "SurveyNumber")
	private String surveyNumber;

	@Column(name = "AreaAcres")
	private Double areaAcres;

	@Column(name = "SoilType")
	private String soilType;

	@Column(name = "IrrigationSource")
	private String irrigationSource;

	@Column(name = "OwnershipType")
	private String ownershipType;

	@Column(name = "Status")
	private String status;
}
