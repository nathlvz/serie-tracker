package mx.com.beni.serietracker.commoncatalogservice.repository;

import org.springframework.data.repository.NoRepositoryBean;

import mx.com.beni.serietracker.commoncatalogservice.entity.CountryEntity;
import mx.com.beni.serietracker.commoncatalogservice.repository.rowmapper.Countable;
import mx.com.beni.serietracker.commoncatalogservice.repository.rowmapper.OperationsRepository;
import mx.com.beni.serietracker.commoncatalogservice.repository.rowmapper.PaginatedRecord;

/**
 * Main contract for declare all the operations that will be allowed for a
 * country or countries.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
//This annotation is for exclude of spring data
@NoRepositoryBean
public interface CountryRepository extends OperationsRepository<CountryEntity, Long>, Countable, PaginatedRecord<CountryEntity> {

	
}
