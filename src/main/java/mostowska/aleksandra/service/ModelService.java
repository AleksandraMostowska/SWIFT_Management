package mostowska.aleksandra.service;

import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.model.dto.CreateModelDto;
import mostowska.aleksandra.model.dto.country.GetGroupedByCountryDto;
import mostowska.aleksandra.model.dto.GetModelDto;
import mostowska.aleksandra.repository.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The ModelService class provides the core business logic for managing bank model data,
 * including creating, retrieving, and deleting models such as Headquarters and Branch.
 * It interacts with the repository layer and DTOs to process data and ensure the integrity
 * of business rules related to model creation and retrieval.
 *
 * The service is marked as a Spring `@Service` to denote it as a Spring-managed bean,
 * and it uses the `@RequiredArgsConstructor` annotation to automatically generate a constructor
 * with required dependencies injected by Spring.
 */
@Service
@RequiredArgsConstructor
public class ModelService {
    private final HeadquartersService headquartersService;
    private final BranchService branchService;
    private final Utils utilsRepository;

    /**
     * Saves a model (either Headquarters or Branch) to the database based on the provided DTO.
     * It first validates that all required fields are present, then determines whether
     * the model is a Headquarter or Branch based on the SWIFT code and calls the respective service
     * for saving it to the database.
     *
     * @param createModelDto DTO containing the data to be saved.
     * @return A DTO representing the saved model (Headquarter or Branch).
     * @throws IllegalStateException If the DTO is null or missing required fields.
     */
    public GetModelDto saveModelToDB(CreateModelDto createModelDto) {
        if (createModelDto == null) {
            throw new IllegalStateException("All required parameters must be passed in form:" +
                    "{\n" +
                    "    \"address\": string,\n" +
                    "    \"bankName\": string,\n" +
                    "    \"countryISO2\": string,\n" +
                    "    \"countryName\": string,\n" +
                    "    \"isHeadquarter\": bool,\n" +
                    "    \"swiftCode\": string,\n" +
                    "}\n");
        }

        CreateModelDto.checkRequiredFields(createModelDto);

        return createModelDto.checkIfHeadquarter() ?
                headquartersService.addHeadquarters(createModelDto, createModelDto.swiftCode())
                : branchService.addBranch(createModelDto, createModelDto.swiftCode());

    }

    /**
     * Retrieves a list of all Headquarters and Branches associated with a given SWIFT code.
     * If the SWIFT code ends with "XXX", it treats it as a Headquarter and includes all related branches.
     *
     * @param swiftCode The SWIFT code used to retrieve the models.
     * @return A list of DTOs representing Headquarters and Branches.
     */
    public List<GetModelDto> getAllHeadquartersWithBranches(String swiftCode) {
        if (swiftCode.endsWith("XXX")) {
            var headquartersList = headquartersService.getAllBySwiftCode(swiftCode);
            var branches = branchService.getAllBySwiftCode(swiftCode);

            return headquartersList.stream()
                    .map(h -> h.withBranches(branches))
                    .collect(Collectors.toList());
        }


        return branchService.getAllBySwiftCode(swiftCode)
                .stream()
                .map(b -> (GetModelDto) b)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all Headquarters and Branches grouped by country using the provided country ISO2 code.
     * Combines the Headquarters and Branches into one list and creates a DTO with the country information.
     *
     * @param countryIso2 The 2-letter ISO country code used to group the models.
     * @return A DTO containing the country information and a list of all related Headquarters and Branches.
     * @throws IllegalStateException If the countryISO2 is null.
     */
    public GetGroupedByCountryDto getAllByCountry(String countryIso2) {
        if (countryIso2 == null) {
            throw new IllegalStateException("CountryISO2 must be passed");
        }

        var headquarters = headquartersService.getAllByCountry(countryIso2);
        var branches = branchService.getAllByCountry(countryIso2);
        var countryName = utilsRepository.findCountryNameByISO2(countryIso2);
        var allBranchesAndHeadquarters = Stream
                .concat(headquarters.stream(), branches.stream())
                .collect(Collectors.toList());


        return new GetGroupedByCountryDto(countryIso2, countryName, allBranchesAndHeadquarters);
    }

    /**
     * Deletes a model (Headquarter or Branch) from the database based on the provided SWIFT code.
     * If the SWIFT code ends with "XXX", it will delete a Headquarter; otherwise, it deletes a Branch.
     *
     * @param swiftCode The SWIFT code used to identify the model to be deleted.
     * @return A DTO representing the deleted model (Headquarter or Branch).
     */
    public GetModelDto delete(String swiftCode) {
        return swiftCode.endsWith("XXX") ?
                headquartersService.removeHeadquarters(swiftCode)
                : branchService.removeBranch(swiftCode);
    }

}
