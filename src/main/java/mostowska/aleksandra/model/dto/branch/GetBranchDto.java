package mostowska.aleksandra.model.dto.branch;

import mostowska.aleksandra.model.dto.GetModelDto;

/**
 * GetBranchDto is a data transfer object (DTO) representing the details of a bank branch.
 */
public record GetBranchDto(
        String address,
        String bankName,
        String countryISO2,
        String countryName,
        Boolean isHeadquarter,
        String swiftCode
) implements GetModelDto {

    /**
     * Converts the current GetBranchDto to a GetBranchForHeadquarterDto.
     *
     * @return A new GetBranchForHeadquarterDto containing the relevant data.
     */
    public GetBranchForHeadquarterDto toGetBranchForHeadquarterDto() {
        return new GetBranchForHeadquarterDto(address, bankName, countryISO2, isHeadquarter, swiftCode);
    }
}
