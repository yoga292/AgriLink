package com.cognizant.agrilink.produce.entity;

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
@Table(name = "ProduceListing")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduceListing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ListingID")
	private Integer listingId;

	@Column(name = "FarmerID")
	private Integer farmerId;

	@Column(name = "CropID")
	private Integer cropId;

	@Column(name = "HarvestDate")
	private LocalDate harvestDate;

	@Column(name = "QuantityKg")
	private Double quantityKg;

	@Column(name = "QualityGrade")
	private String qualityGrade;

	@Column(name = "AskingPricePerKg")
	private Double askingPricePerKg;

	@Column(name = "Status")
	private String status;
}
