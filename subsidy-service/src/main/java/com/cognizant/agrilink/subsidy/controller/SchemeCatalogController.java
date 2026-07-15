package com.cognizant.agrilink.subsidy.controller;

import com.cognizant.agrilink.subsidy.dto.MessageResponse;
import com.cognizant.agrilink.subsidy.dto.SchemeCatalogDto;
import com.cognizant.agrilink.subsidy.entity.SchemeCatalog;
import com.cognizant.agrilink.subsidy.service.SchemeCatalogService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheme-catalogs")
public class SchemeCatalogController {

	private final SchemeCatalogService schemeCatalogService;

	public SchemeCatalogController(SchemeCatalogService schemeCatalogService) {
		this.schemeCatalogService = schemeCatalogService;
	}

	// GET methods return full data
	@GetMapping
	public ResponseEntity<List<SchemeCatalog>> getAll() {
		return ResponseEntity.ok(schemeCatalogService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SchemeCatalog> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(schemeCatalogService.getById(id));
	}

	// Non-GET methods return only a message
	@PostMapping
	public ResponseEntity<MessageResponse> create(@RequestBody SchemeCatalogDto dto) {
		schemeCatalogService.create(dto);
		return ResponseEntity.ok(new MessageResponse("SchemeCatalog created successfully"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody SchemeCatalogDto dto) {
		schemeCatalogService.update(id, dto);
		return ResponseEntity.ok(new MessageResponse("SchemeCatalog updated successfully"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
		schemeCatalogService.delete(id);
		return ResponseEntity.ok(new MessageResponse("SchemeCatalog deleted successfully"));
	}
}
