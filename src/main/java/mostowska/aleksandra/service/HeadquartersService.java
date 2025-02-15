package mostowska.aleksandra.service;

import mostowska.aleksandra.model.dto.CreateModelDto;
import mostowska.aleksandra.model.dto.country.GetModelForCountryDto;
import mostowska.aleksandra.model.dto.headquarters.GetHeadquartersDto;

import java.util.List;

/**
 * The HeadquartersService interface defines the core operations for managing Headquarters models.
 * It provides methods for adding, removing, and retrieving Headquarters data.
 *
 * The service layer responsible for implementing these methods will handle the actual
 * logic related to managing the data, interacting with repositories, and ensuring business rules.
 */
public interface HeadquartersService {

    /**
     * Adds a new Headquarters using the provided DTO and SWIFT code.
     * The method is expected to process the provided data and store a new Headquarters record.
     *
     * @param createModelDto DTO containing the data to create the new Headquarters.
     * @param swiftCode The SWIFT code associated with the Headquarters.
     * @return A DTO representing the added Headquarters.
     */
    GetHeadquartersDto addHeadquarters(CreateModelDto createModelDto, String swiftCode);

    /**
     * Removes a Headquarters based on the provided SWIFT code.
     * The method is expected to delete the Headquarters record from the system.
     *
     * @param swiftCode The SWIFT code associated with the Headquarters to be removed.
     * @return A DTO representing the deleted Headquarters.
     */
    GetHeadquartersDto removeHeadquarters(String swiftCode);

    /**
     * Retrieves a list of Headquarters by the given SWIFT code.
     * This method returns all Headquarters records associated with the provided SWIFT code.
     *
     * @param swiftCode The SWIFT code used to filter Headquarters records.
     * @return A list of DTOs representing Headquarters associated with the given SWIFT code.
     */
    List<GetHeadquartersDto> getAllBySwiftCode(String swiftCode);

    /**
     * Retrieves all Headquarters.
     * This method returns a list of all Headquarters records in the system.
     *
     * @return A list of DTOs representing all Headquarters.
     */
    List<GetHeadquartersDto> getAll();

    /**
     * Retrieves a list of Headquarters by the given country ISO2 code.
     * This method returns all Headquarters records associated with a specific country.
     *
     * @param countryIso2 The 2-letter ISO country code used to filter Headquarters records.
     * @return A list of DTOs representing Headquarters for the given country.
     */
    List<GetModelForCountryDto> getAllByCountry(String countryIso2);
}
