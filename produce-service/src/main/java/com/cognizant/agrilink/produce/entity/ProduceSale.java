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
@Table(name = "ProduceSale")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduceSale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SaleID")
	private Integer saleId;

	@Column(name = "ListingID")
	private Integer listingId;

	@Column(name = "BuyerID")
	private Integer buyerId;

	@Column(name = "QuantitySoldKg")
	private Double quantitySoldKg;

	@Column(name = "AgreedPricePerKg")
	private Double agreedPricePerKg;

	@Column(name = "TotalAmount")
	private Double totalAmount;

	@Column(name = "SaleDate")
	private LocalDate saleDate;

	@Column(name = "PaymentStatus")
	private String paymentStatus;
}
