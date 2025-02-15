package mostowska.aleksandra.model.dto.country;


import java.util.List;

/**
 * GetGroupedByCountryDto is a data transfer object (DTO) that represents a grouping of model data by country.
 */
public record GetGroupedByCountryDto(
        String countryISO2,
        String countryName,
        List<GetModelForCountryDto> items
) {
}
