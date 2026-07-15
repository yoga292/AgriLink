package com.cognizant.agrilink.subsidy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cognizant.agrilink.subsidy.dto.SchemeCatalogDto;
import com.cognizant.agrilink.subsidy.entity.SchemeCatalog;
import com.cognizant.agrilink.subsidy.service.SchemeCatalogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class SchemeCatalogControllerExtendedTest {

	@Mock
	private SchemeCatalogService schemeCatalogService;

	@InjectMocks
	private SchemeCatalogController schemeCatalogController;

	private MockMvc mockMvc;
	private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

	private SchemeCatalog schemeCatalog;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(schemeCatalogController).build();
		schemeCatalog = SchemeCatalog.builder()
				.schemeId(1)
				.schemeName("PM Kisan")
				.category("InputSubsidy")
				.eligibilityCriteria("Small and marginal farmers")
				.benefitAmount(6000.0)
				.fundingSource("Central")
				.startDate(LocalDate.of(2026, 1, 1))
				.endDate(LocalDate.of(2026, 12, 31))
				.status("Active")
				.build();
	}

	@Test
	void getByIdReturnsAllFields() throws Exception {
		when(schemeCatalogService.getById(1)).thenReturn(schemeCatalog);

		mockMvc.perform(get("/scheme-catalogs/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.schemeId").value(1))
				.andExpect(jsonPath("$.schemeName").value("PM Kisan"))
				.andExpect(jsonPath("$.category").value("InputSubsidy"))
				.andExpect(jsonPath("$.eligibilityCriteria").value("Small and marginal farmers"))
				.andExpect(jsonPath("$.benefitAmount").value(6000.0))
				.andExpect(jsonPath("$.fundingSource").value("Central"))
				.andExpect(jsonPath("$.startDate").value("2026-01-01"))
				.andExpect(jsonPath("$.endDate").value("2026-12-31"))
				.andExpect(jsonPath("$.status").value("Active"));
	}

	@Test
	void getAllReturnsAllFields() throws Exception {
		when(schemeCatalogService.getAll()).thenReturn(List.of(schemeCatalog));

		mockMvc.perform(get("/scheme-catalogs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].schemeId").value(1))
				.andExpect(jsonPath("$[0].schemeName").value("PM Kisan"))
				.andExpect(jsonPath("$[0].category").value("InputSubsidy"))
				.andExpect(jsonPath("$[0].benefitAmount").value(6000.0))
				.andExpect(jsonPath("$[0].status").value("Active"));
	}

	@Test
	void getAllEmptyReturnsEmptyArray() throws Exception {
		when(schemeCatalogService.getAll()).thenReturn(List.of());

		mockMvc.perform(get("/scheme-catalogs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	void getAllManyReturnsAll() throws Exception {
		when(schemeCatalogService.getAll()).thenReturn(List.of(schemeCatalog, schemeCatalog, schemeCatalog));

		mockMvc.perform(get("/scheme-catalogs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3));
	}

	@ParameterizedTest
	@ValueSource(strings = {"Active", "Closed", "Upcoming"})
	void getByIdReturnsVariousStatuses(String statusValue) throws Exception {
		schemeCatalog.setStatus(statusValue);
		when(schemeCatalogService.getById(1)).thenReturn(schemeCatalog);

		mockMvc.perform(get("/scheme-catalogs/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value(statusValue));
	}

	@ParameterizedTest
	@ValueSource(strings = {"InputSubsidy", "CropInsurance", "EquipmentGrant", "WelfareSupport"})
	void getByIdReturnsVariousCategories(String category) throws Exception {
		schemeCatalog.setCategory(category);
		when(schemeCatalogService.getById(1)).thenReturn(schemeCatalog);

		mockMvc.perform(get("/scheme-catalogs/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.category").value(category));
	}

	@ParameterizedTest
	@ValueSource(doubles = {0.0, 1000.0, 99999.99, 1000000.0})
	void getByIdReturnsVariousBenefitAmounts(double amount) throws Exception {
		schemeCatalog.setBenefitAmount(amount);
		when(schemeCatalogService.getById(1)).thenReturn(schemeCatalog);

		mockMvc.perform(get("/scheme-catalogs/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.benefitAmount").value(amount));
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 5, 50, 9999})
	void getByIdWithVariousIds(int id) throws Exception {
		schemeCatalog.setSchemeId(id);
		when(schemeCatalogService.getById(id)).thenReturn(schemeCatalog);

		mockMvc.perform(get("/scheme-catalogs/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.schemeId").value(id));
	}

	@Test
	void createReturnsMessageOnlyAndNoEntityFields() throws Exception {
		when(schemeCatalogService.create(any(SchemeCatalogDto.class))).thenReturn(schemeCatalog);

		mockMvc.perform(post("/scheme-catalogs")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new SchemeCatalogDto())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("SchemeCatalog created successfully"))
				.andExpect(jsonPath("$.schemeId").doesNotExist())
				.andExpect(jsonPath("$.schemeName").doesNotExist())
				.andExpect(jsonPath("$.category").doesNotExist())
				.andExpect(jsonPath("$.benefitAmount").doesNotExist())
				.andExpect(jsonPath("$.status").doesNotExist());
	}

	@Test
	void createInvokesServiceCreate() throws Exception {
		when(schemeCatalogService.create(any(SchemeCatalogDto.class))).thenReturn(schemeCatalog);

		mockMvc.perform(post("/scheme-catalogs")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new SchemeCatalogDto())))
				.andExpect(status().isOk());

		verify(schemeCatalogService).create(any(SchemeCatalogDto.class));
	}

	@Test
	void updateReturnsMessageOnlyAndNoEntityFields() throws Exception {
		when(schemeCatalogService.update(eq(1), any(SchemeCatalogDto.class))).thenReturn(schemeCatalog);

		mockMvc.perform(put("/scheme-catalogs/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new SchemeCatalogDto())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("SchemeCatalog updated successfully"))
				.andExpect(jsonPath("$.schemeId").doesNotExist())
				.andExpect(jsonPath("$.schemeName").doesNotExist())
				.andExpect(jsonPath("$.status").doesNotExist());
	}

	@Test
	void updateInvokesServiceUpdate() throws Exception {
		when(schemeCatalogService.update(eq(1), any(SchemeCatalogDto.class))).thenReturn(schemeCatalog);

		mockMvc.perform(put("/scheme-catalogs/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new SchemeCatalogDto())))
				.andExpect(status().isOk());

		verify(schemeCatalogService).update(eq(1), any(SchemeCatalogDto.class));
	}

	@Test
	void deleteReturnsMessageOnlyAndNoEntityFields() throws Exception {
		mockMvc.perform(delete("/scheme-catalogs/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("SchemeCatalog deleted successfully"))
				.andExpect(jsonPath("$.schemeId").doesNotExist())
				.andExpect(jsonPath("$.schemeName").doesNotExist());
	}

	@Test
	void deleteInvokesServiceDelete() throws Exception {
		mockMvc.perform(delete("/scheme-catalogs/1"))
				.andExpect(status().isOk());

		verify(schemeCatalogService).delete(1);
	}

	@ParameterizedTest
	@CsvSource({
		"1,SchemeCatalog deleted successfully",
		"5,SchemeCatalog deleted successfully",
		"99,SchemeCatalog deleted successfully"
	})
	void deleteVariousIdsReturnsMessage(int id, String message) throws Exception {
		mockMvc.perform(delete("/scheme-catalogs/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value(message));

		verify(schemeCatalogService).delete(id);
	}
}
