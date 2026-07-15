package com.cognizant.agrilink.farmer.entity;

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
@Table(name = "FarmerProfile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmerProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FarmerID")
	private Integer farmerId;

	@Column(name = "UserID")
	private Integer userId;

	@Column(name = "Name")
	private String name;

	@Column(name = "DateOfBirth")
	private LocalDate dateOfBirth;

	@Column(name = "Gender")
	private String gender;

	@Column(name = "NationalIDNumber")
	private String nationalIdNumber;

	@Column(name = "Village")
	private String village;

	@Column(name = "District")
	private String district;

	@Column(name = "State")
	private String state;

	@Column(name = "Phone")
	private String phone;

	@Column(name = "BankAccountNumber")
	private String bankAccountNumber;

	@Column(name = "Status")
	private String status;
}
