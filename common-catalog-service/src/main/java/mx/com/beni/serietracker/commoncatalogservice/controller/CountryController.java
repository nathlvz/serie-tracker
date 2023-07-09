package mx.com.beni.serietracker.commoncatalogservice.controller;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mx.com.beni.serietracker.commoncatalogservice.model.CountryModel;
import mx.com.beni.serietracker.commoncatalogservice.model.OperationResult;
import mx.com.beni.serietracker.commoncatalogservice.service.CountryService;

/**
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
@Validated
@RestController
@RequestMapping(path = "/countries")
@RequiredArgsConstructor
public class CountryController {

	/**
	 * 
	 */
	private final CountryService countryService;

	/**
	 * 
	 * @param pageable
	 * @return
	 */
	// @formatter:off
	// The qualifier-delimiter property is a very special case.
	// We can use the @Qualifier annotation on a Pageable method argument to provide
	// a local prefix for the paging query parameters:
	@GetMapping
	public ResponseEntity<Page<CountryModel>> getCountriesPaginated(
			@Qualifier("country") final Pageable pageable) {
		return ok(this.countryService.getCountriesPaginated(pageable));
	}
	// @formatter:on

	// @formatter:off
	@GetMapping("/{countryId}")
	public ResponseEntity<CountryModel> getCountry(
			@PathVariable("countryId") final long countryId) {
		return ok(this.countryService.get(countryId));
	}
	// @formatter:on

	/**
	 * 
	 * @param request
	 * @return
	 */
	// @formatter:off
	@PostMapping
	public ResponseEntity<OperationResult<CountryModel>> addNewCountry(
			@Validated(CountryModel.Add.class) @RequestBody CountryModel request) {
		final var result = this.countryService.addNewCountry(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	// @formatter:on

	/**
	 * 
	 * @param request
	 * @return
	 */
	@PutMapping
	public ResponseEntity<OperationResult<CountryModel>> updateCountry(
			@Validated({ CountryModel.Update.class }) @RequestBody CountryModel request) {
		final var result = this.countryService.updateCountry(request);
		return ResponseEntity.ok(result);
	}

	// @formatter:off
	@DeleteMapping("/{countryId}")
	public ResponseEntity<OperationResult<Void>> deleteCountry(
			@PathVariable("countryId") final long countryId) {
		return ok(this.countryService.deleteCountry(countryId));
	}
	// @formatter:on

}
