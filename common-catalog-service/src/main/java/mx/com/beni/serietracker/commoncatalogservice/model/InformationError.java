package mx.com.beni.serietracker.commoncatalogservice.model;

import java.util.List;

public record InformationError(String errorMessage, List<String> additionalErrors) {

}
