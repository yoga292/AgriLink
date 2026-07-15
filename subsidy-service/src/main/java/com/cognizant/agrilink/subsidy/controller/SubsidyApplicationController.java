package com.cognizant.agrilink.subsidy.controller;

import com.cognizant.agrilink.subsidy.dto.MessageResponse;
import com.cognizant.agrilink.subsidy.dto.SubsidyApplicationDto;
import com.cognizant.agrilink.subsidy.entity.SubsidyApplication;
import com.cognizant.agrilink.subsidy.service.SubsidyApplicationService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subsidy-applications")
public class SubsidyApplicationController {

	private static final String ROLE_FARMER = "ROLE_Farmer";

	private final SubsidyApplicationService subsidyApplicationService;

	public SubsidyApplicationController(SubsidyApplicationService subsidyApplicationService) {
		this.subsidyApplicationService = subsidyApplicationService;
	}

	// GET methods return full data.
	// A Farmer only ever sees their own applications; officers/admins see everything.
	@GetMapping
	public ResponseEntity<List<SubsidyApplication>> getAll(Authentication authentication) {
		if (isFarmer(authentication)) {
			return ResponseEntity.ok(subsidyApplicationService.getByUserId(currentUserId(authentication)));
		}
		return ResponseEntity.ok(subsidyApplicationService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SubsidyApplication> getById(@PathVariable Integer id, Authentication authentication) {
		SubsidyApplication application = subsidyApplicationService.getById(id);
		if (isFarmer(authentication) && !currentUserId(authentication).equals(application.getUserId())) {
			throw new AccessDeniedException("You can only view your own subsidy applications");
		}
		return ResponseEntity.ok(application);
	}

	// Non-GET methods return only a message.
	@PostMapping
	public ResponseEntity<MessageResponse> create(@RequestBody SubsidyApplicationDto dto, Authentication authentication) {
		if (isFarmer(authentication)) {
			// A Farmer can only file an application under their own identity, and may not
			// self-record review/disbursement outcomes (those belong to a SubsidyAdmin).
			dto.setUserId(currentUserId(authentication));
			clearDisbursementFields(dto);
		}
		subsidyApplicationService.create(dto);
		return ResponseEntity.ok(new MessageResponse("SubsidyApplication created successfully"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody SubsidyApplicationDto dto,
			Authentication authentication) {
		if (isFarmer(authentication)) {
			Integer userId = currentUserId(authentication);
			SubsidyApplication existing = subsidyApplicationService.getById(id);
			if (!userId.equals(existing.getUserId())) {
				throw new AccessDeniedException("You can only update your own subsidy applications");
			}
			dto.setUserId(userId);
			// Preserve admin-controlled review/disbursement data — a Farmer cannot alter it.
			dto.setReviewedBy(existing.getReviewedBy());
			dto.setDisbursedAmount(existing.getDisbursedAmount());
			dto.setDisbursedDate(existing.getDisbursedDate());
		}
		subsidyApplicationService.update(id, dto);
		return ResponseEntity.ok(new MessageResponse("SubsidyApplication updated successfully"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Integer id, Authentication authentication) {
		if (isFarmer(authentication)) {
			SubsidyApplication existing = subsidyApplicationService.getById(id);
			if (!currentUserId(authentication).equals(existing.getUserId())) {
				throw new AccessDeniedException("You can only delete your own subsidy applications");
			}
		}
		subsidyApplicationService.delete(id);
		return ResponseEntity.ok(new MessageResponse("SubsidyApplication deleted successfully"));
	}

	private boolean isFarmer(Authentication authentication) {
		if (authentication == null) {
			return false;
		}
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (ROLE_FARMER.equals(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	private Integer currentUserId(Authentication authentication) {
		return (Integer) authentication.getPrincipal();
	}

	private void clearDisbursementFields(SubsidyApplicationDto dto) {
		dto.setReviewedBy(null);
		dto.setDisbursedAmount(null);
		dto.setDisbursedDate(null);
	}
}
