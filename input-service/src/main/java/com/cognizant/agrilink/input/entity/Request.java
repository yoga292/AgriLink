package com.cognizant.agrilink.input.entity;

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
@Table(name = "Request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RequestID")
	private Integer requestId;

	@Column(name = "FarmerID")
	private Integer farmerId;

	@Column(name = "InputID")
	private Integer inputId;

	@Column(name = "QuantityRequested")
	private Integer quantityRequested;

	@Column(name = "RequestDate")
	private LocalDate requestDate;

	@Column(name = "AssignedCentreID")
	private Integer assignedCentreId;

	@Column(name = "ActualPrice")
	private Double actualPrice;

	@Column(name = "Status")
	private String status;
}
