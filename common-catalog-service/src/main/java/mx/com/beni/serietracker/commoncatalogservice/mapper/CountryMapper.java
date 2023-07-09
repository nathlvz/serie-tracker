package mx.com.beni.serietracker.commoncatalogservice.mapper;

import org.mapstruct.factory.Mappers;

import mx.com.beni.serietracker.commoncatalogservice.entity.CountryEntity;
import mx.com.beni.serietracker.commoncatalogservice.model.CountryModel;

/**
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
@org.mapstruct.Mapper
public interface CountryMapper {

	/**
	 * 
	 */
	public static final CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

	/**
	 * 
	 * @param model
	 * @return
	 */
	CountryEntity map(CountryModel model);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	CountryModel map(CountryEntity entity);

}
