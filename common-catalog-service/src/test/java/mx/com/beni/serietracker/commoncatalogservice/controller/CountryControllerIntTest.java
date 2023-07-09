package mx.com.beni.serietracker.commoncatalogservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.SneakyThrows;
import mx.com.beni.serietracker.commoncatalogservice.model.CountryModel;
import mx.com.beni.serietracker.commoncatalogservice.service.CountryService;

//@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@WebMvcTest(controllers = { CountryController.class })
public class CountryControllerIntTest {

	@RegisterExtension
	private final RestDocumentationExtension documentation = new RestDocumentationExtension(
			"target/generated-snippets");

	@MockBean
	private CountryService countryService;

	private MockMvc mockMvc;

	// @formatter:off
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, 
			RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//				.alwaysDo(document(, 
//						Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), 
//						Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
				.apply(MockMvcRestDocumentation
						.documentationConfiguration(restDocumentation)).build();
	}
	// @formatter:on

	// @formatter:off
	@Test
	@SneakyThrows
	void test_get_countries_paginated_successful() {
		// Arrange
		final var registrationDateExpected = LocalDateTime.of(2023, Month.APRIL, 30, 12, 30, 12, 100);
		final List<CountryModel> countries = Arrays.asList(new CountryModel(1, "Afghanistan", registrationDateExpected),
				new CountryModel(2, "Aland Islands", registrationDateExpected),
				new CountryModel(3, "Albania", registrationDateExpected));

		when(this.countryService.getCountriesPaginated(eq(of(0, 3, by(asc("country_id"), desc("name"))))))
				.thenReturn(new PageImpl<>(countries));

		// Act & Assert
		// Context path is deduced (/common-catalog)
		this.mockMvc
				.perform(RestDocumentationRequestBuilders.get("/countries").param("country_page", String.valueOf(0))
						.param("country_size", String.valueOf(3)).param("country_sort", "country_id,asc")
						.param("country_sort", "name,desc"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpectAll(
						jsonPath("$.content.[0].countryId").value(1),
						jsonPath("$.content.[0].name").value("Afghanistan"),
						jsonPath("$.content.[0].registrationDate").exists()
						)
				.andDo(document("{class_name}/{method-name}/",
//						  Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), 
						Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
						PayloadDocumentation.responseFields(
								PayloadDocumentation.fieldWithPath("pageable").description("----"),
								PayloadDocumentation.fieldWithPath("last").description("----"),
								PayloadDocumentation.fieldWithPath("totalElements").description("----"),
								PayloadDocumentation.fieldWithPath("totalPages").description("----"),
								PayloadDocumentation.fieldWithPath("size").description("----"),
								PayloadDocumentation.fieldWithPath("number").description("----"),
								PayloadDocumentation.fieldWithPath("first").description("----"),
								PayloadDocumentation.fieldWithPath("numberOfElements").description("----"),
								PayloadDocumentation.fieldWithPath("empty").description("----"),
								PayloadDocumentation.fieldWithPath("sort").description("----"),
								PayloadDocumentation.fieldWithPath("sort.empty").description("----"),
								PayloadDocumentation.fieldWithPath("sort.sorted").description("----"),
								PayloadDocumentation.fieldWithPath("sort.unsorted").description("----"),
								PayloadDocumentation.fieldWithPath("content").description("The slice of data, in this case the slice of countries."),
								PayloadDocumentation.fieldWithPath("content[0].countryId").description("----"),
								PayloadDocumentation.fieldWithPath("content[0].name").description("----"),
								PayloadDocumentation.fieldWithPath("content[0].registrationDate").description("----")
								)
				));

		final ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(this.countryService).getCountriesPaginated(pageableCaptor.capture());
		final var sort = pageableCaptor.getValue().getSort();

		assertThat(sort.getOrderFor("country_id")).matches((final var orderCountryId) -> {
			return orderCountryId.getDirection() == Direction.ASC;
		});
		assertThat(sort.getOrderFor("name")).matches((final var orderName) -> {
			return orderName.getDirection() == Direction.DESC;
		});
	}
	// @formatter:on

}

//@WebMvcTest(controllers = { CountryController.class })
//public class CountryControllerIntTest {
//
//	@MockBean
//	private CountryService countryService;
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	// @formatter:off
//	@Test
//	@SneakyThrows
//	void test_get_countries_paginated_successful() {
//		// Arrange
//		final var registrationDate = LocalDateTime.of(2023, Month.JULY, 4, 22, 58, 19);
//		final List<CountryModel> countries = Arrays.asList(new CountryModel(1, "Afghanistan", registrationDate),
//				new CountryModel(2, "Aland Islands", registrationDate),
//				new CountryModel(3, "Albania", registrationDate));
//
//		when(this.countryService.getCountriesPaginated(eq(of(0, 3, by(asc("country_id"), desc("name"))))))
//				.thenReturn(new PageImpl<>(countries));
//
//		// Act & Assert
//		// Context path is deduced (/common-catalog)
//		this.mockMvc
//				.perform(MockMvcRequestBuilders.get("/countries")
//						.param("country_page", String.valueOf(0))
//						.param("country_size", String.valueOf(3))
//						.param("country_sort", "country_id,asc")
//						.param("country_sort", "name,desc"))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//				.andExpectAll(
//						jsonPath("$.content.[0].countryId").value(1), 
//						jsonPath("$.content.[0].name").value("Afghanistan"),
//						jsonPath("$.content.[0].registrationDate")
//							.value(registrationDate.toString())
//				);
//	
//		final ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
//		verify(this.countryService).getCountriesPaginated(pageableCaptor.capture());
//		final var sort = pageableCaptor.getValue().getSort();
//
//		assertThat(sort.getOrderFor("country_id")).matches((final var orderCountryId) -> {
//			return orderCountryId.getDirection() == Direction.ASC;
//		});
//		assertThat(sort.getOrderFor("name")).matches((final var orderName) -> {
//			return orderName.getDirection() == Direction.DESC;
//		});
//	}
//	// @formatter:on
//
//}
