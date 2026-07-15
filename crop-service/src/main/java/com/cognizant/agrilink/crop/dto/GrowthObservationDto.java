package com.cognizant.agrilink.crop.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowthObservationDto {

	private Integer observationId;
	private Integer planId;
	private Integer officerId;
	private LocalDate observationDate;
	private String stage;
	private Boolean pestOrDiseaseFlag;
	private String remarks;
}
