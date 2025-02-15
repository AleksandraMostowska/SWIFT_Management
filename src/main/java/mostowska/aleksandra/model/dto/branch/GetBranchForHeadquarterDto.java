package mostowska.aleksandra.model.dto.branch;

import mostowska.aleksandra.model.dto.GetModelDto;

/**
 * GetBranchForHeadquarterDto is a data transfer object (DTO) representing the details of a bank branch
 * that serves as the headquarter of the bank.
 */
public record GetBranchForHeadquarterDto(
        String address,
        String bankName,
        String countryISO2,
        Boolean isHeadquarter,
        String swiftCode
) implements GetModelDto {
}
