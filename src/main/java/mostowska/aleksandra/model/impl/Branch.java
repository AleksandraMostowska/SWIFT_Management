package mostowska.aleksandra.model.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mostowska.aleksandra.model.Model;
import mostowska.aleksandra.model.dto.branch.GetBranchDto;

/**
 * The Branch class represents a bank branch and contains the necessary attributes
 * for identifying a bank branch, such as its address, name, country information,
 * SWIFT code, and whether it's a headquarter.
 *
 * It is built using Lombok annotations to simplify the creation of the class:
 * - `@AllArgsConstructor`: Generates a constructor that takes all fields as parameters.
 * - `@Setter`: Automatically generates setter methods for all fields.
 * - `@SuperBuilder`: Provides a builder pattern for creating instances of this class.
 */
@AllArgsConstructor
@Setter
@SuperBuilder
public class Branch extends Model {

    /**
     * Converts the current Branch object to a GetBranchDto, which is a Data Transfer
     * Object (DTO) used to represent branch data in a more suitable format for communication
     * (e.g., for API responses).
     *
     * @return A new GetBranchDto populated with data from the current Branch instance.
     */
    public GetBranchDto toGetBranchDto() {
        return new GetBranchDto(address, bankName, countryIso2, countryName, isHeadquarter, swiftCode);
    }

    /**
     * Provides a string representation of the Branch object. This method overrides
     * the `toString` method from the `Model` class and returns a string representation
     * of the current object by calling the `toString` method of the superclass.
     *
     * @return A string representation of the Branch object.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
