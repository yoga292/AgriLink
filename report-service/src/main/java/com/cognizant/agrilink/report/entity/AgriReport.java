package com.cognizant.agrilink.report.entity;

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
@Table(name = "AgriReport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgriReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReportID")
	private Integer reportId;

	@Column(name = "GeneratedBy")
	private Integer generatedBy;

	@Column(name = "Scope")
	private String scope;

	@Column(name = "Metrics")
	private String metrics;

	@Column(name = "GeneratedDate")
	private LocalDate generatedDate;
}
