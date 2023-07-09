package mx.com.beni.serietracker.commoncatalogservice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mx.com.beni.serietracker.commoncatalogservice.exception.NotDeletedException;
import mx.com.beni.serietracker.commoncatalogservice.exception.NotFoundException;
import mx.com.beni.serietracker.commoncatalogservice.exception.NotUpdatedException;
import mx.com.beni.serietracker.commoncatalogservice.exception.UnregisteredRecordException;
import mx.com.beni.serietracker.commoncatalogservice.mapper.CountryMapper;
import mx.com.beni.serietracker.commoncatalogservice.model.CountryModel;
import mx.com.beni.serietracker.commoncatalogservice.model.OperationResult;
import mx.com.beni.serietracker.commoncatalogservice.repository.CountryRepository;

/**
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

	/**
	 * 
	 */
	private final CountryRepository countryRepository;

	/**
	 * 
	 */
	private static final String SUCCESSFUL_MESSAGE_ADD_NEW_COUNTRY = "The country was successfully registered.";

	/**
	 * 
	 */
	private static final String ERROR_MESSAGE_ADD_NEW_COUNTRY = "The country was not registered.";

	/**
	 * 
	 */
	private static final String SUCCESSFUL_MESSAGE_UPDATE_COUNTRY = "The country was successfully updated.";

	/**
	 * 
	 */
	private static final String ERROR_MESSAGE_UPDATE_COUNTRY = "The country was not updated.";

	/**
	 * 
	 */
	private static final String SUCCESSFUL_MESSAGE_DELETE_COUNTRY = "The country was successfully deleted.";

	/**
	 * 
	 */
	private static final String ERROR_MESSAGE_DELETE_COUNTRY = "The country was not deleted.";

	/**
	 * 
	 */
	private static final String ERROR_MESSAGE_GET_COUNTRY = "The country was not found.";

	/**
	 * 
	 */
	@Transactional
	@Override
	public OperationResult<CountryModel> addNewCountry(CountryModel model) {
		final var entity = CountryMapper.INSTANCE.map(model);
		return Optional.ofNullable(this.countryRepository.insert(entity)).map((final var countryEntity) -> {
			final var modelRetrived = CountryMapper.INSTANCE.map(countryEntity);
			return new OperationResult<CountryModel>(SUCCESSFUL_MESSAGE_ADD_NEW_COUNTRY, true, modelRetrived);
		}).orElseThrow(() -> new UnregisteredRecordException(ERROR_MESSAGE_ADD_NEW_COUNTRY));

	}

	/**
	 * 
	 */
	@Transactional
	@Override
	public OperationResult<CountryModel> updateCountry(CountryModel model) {
		final var oldRecord = this.countryRepository.get(model.getCountryId());
		if (oldRecord.isEmpty()) {
			throw new NotFoundException(ERROR_MESSAGE_GET_COUNTRY);
		}
		final var oldValueRecord = oldRecord.get();
		final var entity = CountryMapper.INSTANCE.map(model);
		return Optional.ofNullable(this.countryRepository.update(entity)).map((final var countryEntity) -> {
			final var modelRetrived = CountryMapper.INSTANCE.map(countryEntity);
			modelRetrived.setRegistrationDate(oldValueRecord.registrationDate());
			return new OperationResult<CountryModel>(SUCCESSFUL_MESSAGE_UPDATE_COUNTRY, true, modelRetrived);
		}).orElseThrow(() -> new NotUpdatedException(ERROR_MESSAGE_UPDATE_COUNTRY));
	}

	/**
	 * 
	 */
	@Transactional
	@Override
	public OperationResult<Void> deleteCountry(long id) {
		if (!this.countryRepository.exist(id)) {
			throw new NotFoundException(ERROR_MESSAGE_GET_COUNTRY);
		}
		if (!this.countryRepository.delete(id)) {
			throw new NotDeletedException(ERROR_MESSAGE_DELETE_COUNTRY);
		}
		return new OperationResult<Void>(SUCCESSFUL_MESSAGE_DELETE_COUNTRY, true, null);
	}

	/**
	 * 
	 */
	// @formatter:off
	@Transactional(readOnly = true)
	@Override
	public CountryModel get(long id) {
		return this.countryRepository.get(id)
			.map(CountryMapper.INSTANCE::map)
			.orElseThrow(() -> new NotFoundException(ERROR_MESSAGE_GET_COUNTRY));
	}
	// @formatter:on

	/**
	 * 
	 */
	@Transactional(readOnly = true)
	@Override
	public Page<CountryModel> getCountriesPaginated(final Pageable pageable) {
		return this.countryRepository.getPage(pageable).map(CountryMapper.INSTANCE::map);
	}

}
