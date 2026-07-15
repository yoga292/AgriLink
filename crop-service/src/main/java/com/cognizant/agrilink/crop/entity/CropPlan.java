package com.cognizant.agrilink.crop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CropPlan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PlanID")
	private Integer planId;

	@Column(name = "FarmerID")
	private Integer farmerId;

	@Column(name = "HoldingID")
	private Integer holdingId;

	@Column(name = "CropID")
	private Integer cropId;

	@Column(name = "Season")
	private String season;

	@Column(name = "Year")
	private Integer year;

	@Column(name = "SowingDate")
	private LocalDate sowingDate;

	@Column(name = "ExpectedHarvestDate")
	private LocalDate expectedHarvestDate;

	@Column(name = "AreaPlanted")
	private Double areaPlanted;

	@Column(name = "Status")
	private String status;
}
