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
@Table(name = "GrowthObservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowthObservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ObservationID")
	private Integer observationId;

	@Column(name = "PlanID")
	private Integer planId;

	@Column(name = "OfficerID")
	private Integer officerId;

	@Column(name = "ObservationDate")
	private LocalDate observationDate;

	@Column(name = "Stage")
	private String stage;

	@Column(name = "PestOrDiseaseFlag")
	private Boolean pestOrDiseaseFlag;

	@Column(name = "Remarks")
	private String remarks;
}
