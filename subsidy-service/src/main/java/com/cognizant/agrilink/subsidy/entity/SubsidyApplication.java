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
@Table(name = "SubsidyApplication")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubsidyApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ApplicationID")
	private Integer applicationId;

	@Column(name = "FarmerID")
	private Integer farmerId;

	// Owning IAM user (JWT subject) — used for object-level ownership checks.
	@Column(name = "UserID")
	private Integer userId;

	@Column(name = "SchemeID")
	private Integer schemeId;

	@Column(name = "ApplicationDate")
	private LocalDate applicationDate;

	@Column(name = "EligibilityScore")
	private Double eligibilityScore;

	@Column(name = "ReviewedBy")
	private Integer reviewedBy;

	@Column(name = "DisbursedAmount")
	private Double disbursedAmount;

	@Column(name = "DisbursedDate")
	private LocalDate disbursedDate;

	@Column(name = "Status")
	private String status;
}
