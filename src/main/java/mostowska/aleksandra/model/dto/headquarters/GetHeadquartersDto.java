package mostowska.aleksandra.model.dto.headquarters;


import mostowska.aleksandra.model.dto.GetModelDto;
import mostowska.aleksandra.model.dto.branch.GetBranchDto;
import mostowska.aleksandra.model.dto.branch.GetBranchForHeadquarterDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GetHeadquartersDto is a data transfer object (DTO) representing the details of a bank's headquarter.
 * It includes information about the headquarter itself as well as the associated branches.
 */
public record GetHeadquartersDto(
        String address,
        String bankName,
        String countryISO2,
        String countryName,
        Boolean isHeadquarter,
        String swiftCode,
        List<GetBranchForHeadquarterDto> branches
) implements GetModelDto {

    /**
     * Associates a list of branches with the headquarter.
     * Converts a list of GetBranchDto to GetBranchForHeadquarterDto.
     *
     * @param branches A list of branches to associate with the headquarter.
     * @return A new GetHeadquartersDto with the updated list of branches.
     */
    public GetHeadquartersDto withBranches(List<GetBranchDto> branches) {
        var branchesForHeadquarters = branches
                .stream()
                .map(GetBranchDto::toGetBranchForHeadquarterDto)
                .collect(Collectors.toList());
        return new GetHeadquartersDto(address, bankName, countryISO2, countryName, isHeadquarter, swiftCode,
                branchesForHeadquarters);
    }
}
