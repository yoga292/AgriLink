package com.cognizant.agrilink.input.entity;

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
@Table(name = "Catalog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Catalog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "InputID")
	private Integer inputId;

	@Column(name = "Name")
	private String name;

	@Column(name = "Category")
	private String category;

	@Column(name = "Unit")
	private String unit;

	@Column(name = "PricePerUnit")
	private Double pricePerUnit;

	@Column(name = "SubsidisedPrice")
	private Double subsidisedPrice;

	@Column(name = "AvailableStock")
	private Integer availableStock;

	@Column(name = "Status")
	private String status;
}
