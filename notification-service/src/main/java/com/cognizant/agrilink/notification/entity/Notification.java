package com.cognizant.agrilink.notification.entity;

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
@Table(name = "Notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NotificationID")
	private Integer notificationId;

	@Column(name = "UserID")
	private Integer userId;

	@Column(name = "Message")
	private String message;

	@Column(name = "Category")
	private String category;

	@Column(name = "Status")
	private String status;

	@Column(name = "CreatedDate")
	private LocalDate createdDate;
}
