package mostowska.aleksandra.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mostowska.aleksandra.model.dto.country.GetModelForCountryDto;

/**
 * The Model class serves as a base class for other entities (e.g., `Branch`, `Headquarters`)
 * that represent bank-related data. It contains common attributes such as country information,
 * SWIFT code, bank name, address, and more. The class is abstract, meaning it is designed
 * to be extended by other classes that will specify more concrete behaviors.
 *
 * - `@NoArgsConstructor`: Generates a no-argument constructor.
 * - `@AllArgsConstructor`: Generates a constructor that takes all fields as parameters.
 * - `@Setter`: Generates setter methods for all fields.
 * - `@SuperBuilder`: Provides a builder pattern for creating instances of subclasses.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@SuperBuilder
public abstract class Model {
    protected String countryIso2;
    protected String swiftCode;
    protected String swiftPrefix;
    protected String codeType;
    protected String bankName;
    protected String address;
    protected String townName;
    protected String countryName;
    protected String timeZone;
    protected Boolean isHeadquarter;

    /**
     * Overrides the `toString` method to return a string representation of the Model object.
     * The string includes all relevant fields of the Model to provide a clear textual description
     * of the object. This method is useful for debugging or logging purposes.
     *
     * @return A string representation of the Model object.
     */
    @Override
    public String toString() {
        return "Model{" +
                "countryISO2='" + countryIso2 + '\'' +
                ", swiftCode='" + swiftCode + '\'' +
                ", swiftPrefix='" + swiftPrefix + '\'' +
                ", codeType='" + codeType + '\'' +
                ", bankName='" + bankName + '\'' +
                ", address='" + address + '\'' +
                ", townName='" + townName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", isHeadquarter=" + isHeadquarter +
                '}';
    }

    /**
     * Converts the current Model object into a GetModelForCountryDto.
     * This DTO is designed to encapsulate key information about the model
     * in a format suitable for data transfer, such as API responses.
     *
     * @return A new GetModelForCountryDto populated with data from the current Model instance.
     */
    public GetModelForCountryDto toGetModelByCountryDto() {
        return new GetModelForCountryDto(address, bankName, countryIso2, isHeadquarter, swiftCode);
    }
}
