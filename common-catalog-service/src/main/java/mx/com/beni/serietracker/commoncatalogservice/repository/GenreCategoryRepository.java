package mx.com.beni.serietracker.commoncatalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.validation.annotation.Validated;

import mx.com.beni.serietracker.commoncatalogservice.entity.GenreCategory;

//@Repository
@RepositoryRestResource(path = "genre-categories", collectionResourceRel = "[collectionResourceRel]", itemResourceRel = "[itemResourceRel]")
@Validated
public interface GenreCategoryRepository extends JpaRepository<GenreCategory, Long>{
//public interface GenreCategoryRepository extends CrudRepository<GenreCategory, Long> {


}
