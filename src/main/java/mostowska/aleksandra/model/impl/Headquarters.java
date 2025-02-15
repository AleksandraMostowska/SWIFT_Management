package mostowska.aleksandra.model.impl;

import lombok.*;
import lombok.experimental.SuperBuilder;
import mostowska.aleksandra.model.Model;
import mostowska.aleksandra.model.dto.headquarters.GetHeadquartersDto;

/**
 * The Headquarters class represents a bank's headquarters entity and contains
 * the necessary attributes to define a bank's head office, such as address, bank name,
 * country details, SWIFT code, and a flag indicating it is a headquarter.
 *
 * It is built using Lombok annotations:
 * - `@AllArgsConstructor`: Generates a constructor that takes all fields as parameters.
 * - `@Setter`: Automatically generates setter methods for all fields.
 * - `@SuperBuilder`: Provides a builder pattern to easily create instances of this class.
 */
@AllArgsConstructor
@Setter
@SuperBuilder
public class Headquarters extends Model {

    /**
     * Converts the current Headquarters object to a GetHeadquartersDto,
     * which is a Data Transfer Object (DTO) used to represent headquarter data
     * in a format suitable for communication, such as API responses.
     *
     * The `branches` field is set to `null` in this method, as the current object represents
     * a headquarter and may not require any branch data at this level.
     *
     * @return A new GetHeadquartersDto populated with data from the current Headquarters instance.
     */
    public GetHeadquartersDto toGetHeadquartersDto() {
        return new GetHeadquartersDto(address, bankName, countryIso2, countryName, isHeadquarter, swiftCode, null);
    }


}
