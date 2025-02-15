package mostowska.aleksandra.service.impl;

import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.model.dto.CreateModelDto;
import mostowska.aleksandra.model.dto.country.GetModelForCountryDto;
import mostowska.aleksandra.model.dto.branch.GetBranchDto;
import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.repository.model.BranchRepository;
import mostowska.aleksandra.service.BranchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the BranchService interface that defines the core logic for managing Branch models.
 * It includes operations such as adding, removing, and retrieving Branch records from a data source.
 * The service relies on the BranchRepository to interact with the persistence layer.
 */
@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    /**
     * Adds a new Branch based on the provided CreateModelDto and SWIFT code.
     * It verifies the input, converts it into a Branch entity, and then saves it to the repository.
     *
     * @param createModelDto DTO containing the information needed to create the Branch.
     * @param swiftCode The SWIFT code associated with the Branch.
     * @return A DTO representing the added Branch.
     */
    @Override
    public GetBranchDto addBranch(CreateModelDto createModelDto, String swiftCode) {
        if (createModelDto == null) {
            throw new IllegalStateException("Item cannot be null");
        }

        createModelDto.verify();

        var branchToAdd = createModelDto.toBranch();
        return branchRepository.save(branchToAdd).toGetBranchDto();
    }

    /**
     * Removes a Branch based on the provided SWIFT code.
     * If the Branch is not found, an exception is thrown. Otherwise, the Branch is deleted.
     *
     * @param swiftCode The SWIFT code associated with the Branch to be removed.
     * @return A DTO representing the deleted Branch.
     */
    @Override
    public GetBranchDto removeBranch(String swiftCode) {
        if (swiftCode == null) {
            throw new IllegalStateException("Removal failed");
        }
        if (branchRepository.findAllForSWIFT(swiftCode).isEmpty()) {
            throw new IllegalStateException("Item not found");
        }

        return branchRepository.delete(swiftCode).toGetBranchDto();
    }

    /**
     * Retrieves a list of Branches associated with the provided SWIFT code.
     * If there are no matching Branches, it returns an empty list.
     *
     * @param swiftCode The SWIFT code used to filter the Branch records.
     * @return A list of DTOs representing the Branches that match the SWIFT code.
     */
    @Override
    public List<GetBranchDto> getAllBySwiftCode(String swiftCode) {
        var branchesFound = branchRepository.findAllForSWIFT(swiftCode);
        return branchesFound
                .stream()
                .map(Branch::toGetBranchDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of Branches associated with the provided country ISO2 code.
     * If there are no matching Branches, it returns an empty list.
     *
     * @param countryIso2 The 2-letter ISO country code used to filter Branch records.
     * @return A list of DTOs representing the Branches that belong to the given country.
     */
    @Override
    public List<GetModelForCountryDto> getAllByCountry(String countryIso2) {
        if (countryIso2 == null) {
            throw new IllegalStateException("Country name must be passed");
        }
        return branchRepository.findAllForCountry(countryIso2)
                .stream()
                .map(Branch::toGetModelByCountryDto)
                .collect(Collectors.toList());
    }
}
