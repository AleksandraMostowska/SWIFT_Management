package mostowska.aleksandra.service;

import mostowska.aleksandra.model.dto.CreateModelDto;
import mostowska.aleksandra.model.dto.country.GetModelForCountryDto;
import mostowska.aleksandra.model.dto.branch.GetBranchDto;

import java.util.List;

/**
 * The BranchService interface defines the core operations for managing Branch models.
 * It provides methods for adding, removing, and retrieving Branch data.
 *
 * The service layer responsible for implementing these methods will handle the actual
 * logic related to managing the data, interacting with repositories, and ensuring business rules.
 */
public interface BranchService {

    /**
     * Adds a new Branch using the provided DTO and SWIFT code.
     * The method processes the provided data and stores a new Branch record.
     *
     * @param createModelDto DTO containing the data to create the new Branch.
     * @param swiftCode The SWIFT code associated with the Branch.
     * @return A DTO representing the added Branch.
     */
    GetBranchDto addBranch(CreateModelDto createModelDto, String swiftCode);

    /**
     * Removes a Branch based on the provided SWIFT code.
     * The method deletes the Branch record from the system.
     *
     * @param swiftCode The SWIFT code associated with the Branch to be removed.
     * @return A DTO representing the deleted Branch.
     */
    GetBranchDto removeBranch(String swiftCode);

    /**
     * Retrieves a list of Branches by the given SWIFT code.
     * This method returns all Branch records associated with the provided SWIFT code.
     *
     * @param swiftCode The SWIFT code used to filter Branch records.
     * @return A list of DTOs representing Branches associated with the given SWIFT code.
     */
    List<GetBranchDto> getAllBySwiftCode(String swiftCode);

    /**
     * Retrieves a list of Branches by the given country ISO2 code.
     * This method returns all Branch records associated with a specific country.
     *
     * @param countryIso2 The 2-letter ISO country code used to filter Branch records.
     * @return A list of DTOs representing Branches for the given country.
     */
    List<GetModelForCountryDto> getAllByCountry(String countryIso2);
}
