package mostowska.aleksandra.model.dto.country;

/**
 * GetModelForCountryDto is a data transfer object (DTO) that represents the details of a bank model
 * associated with a specific country.
 */
public record GetModelForCountryDto(
        String address,
        String bankName,
        String countryISO2,
        Boolean isHeadquarter,
        String swiftCode
) {
}
