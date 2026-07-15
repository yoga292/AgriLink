package com.cognizant.agrilink.report.controller;

import com.cognizant.agrilink.report.dto.AgriReportDto;
import com.cognizant.agrilink.report.dto.MessageResponse;
import com.cognizant.agrilink.report.entity.AgriReport;
import com.cognizant.agrilink.report.service.AgriReportService;
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
@RequestMapping("/agri-reports")
public class AgriReportController {

	private final AgriReportService agriReportService;

	public AgriReportController(AgriReportService agriReportService) {
		this.agriReportService = agriReportService;
	}

	// GET methods return full data
	@GetMapping
	public ResponseEntity<List<AgriReport>> getAll() {
		return ResponseEntity.ok(agriReportService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AgriReport> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(agriReportService.getById(id));
	}

	// Non-GET methods return only a message
	@PostMapping
	public ResponseEntity<MessageResponse> create(@RequestBody AgriReportDto dto) {
		agriReportService.create(dto);
		return ResponseEntity.ok(new MessageResponse("AgriReport created successfully"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody AgriReportDto dto) {
		agriReportService.update(id, dto);
		return ResponseEntity.ok(new MessageResponse("AgriReport updated successfully"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
		agriReportService.delete(id);
		return ResponseEntity.ok(new MessageResponse("AgriReport deleted successfully"));
	}
}
