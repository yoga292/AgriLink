package com.cognizant.agrilink.subsidy.entity;

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
@Table(name = "SchemeCatalog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchemeCatalog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SchemeID")
	private Integer schemeId;

	@Column(name = "SchemeName")
	private String schemeName;

	@Column(name = "Category")
	private String category;

	@Column(name = "EligibilityCriteria")
	private String eligibilityCriteria;

	@Column(name = "BenefitAmount")
	private Double benefitAmount;

	@Column(name = "FundingSource")
	private String fundingSource;

	@Column(name = "StartDate")
	private LocalDate startDate;

	@Column(name = "EndDate")
	private LocalDate endDate;

	@Column(name = "Status")
	private String status;
}
