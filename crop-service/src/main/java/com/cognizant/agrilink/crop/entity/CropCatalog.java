package com.cognizant.agrilink.crop.entity;

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
@Table(name = "CropCatalog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropCatalog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CropID")
	private Integer cropId;

	@Column(name = "CropName")
	private String cropName;

	@Column(name = "Category")
	private String category;

	@Column(name = "Season")
	private String season;

	@Column(name = "TypicalDurationDays")
	private Integer typicalDurationDays;

	@Column(name = "ExpectedYieldPerAcre")
	private Double expectedYieldPerAcre;

	@Column(name = "Status")
	private String status;
}
