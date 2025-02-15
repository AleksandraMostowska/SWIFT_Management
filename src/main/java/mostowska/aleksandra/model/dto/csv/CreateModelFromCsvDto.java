package mostowska.aleksandra.model.dto.csv;

import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.model.impl.Headquarters;

/**
 * CreateModelFromCsvDto is a data transfer object (DTO) that represents the data required to create a model
 * (either a branch or a headquarter) from CSV input.
 */
public record CreateModelFromCsvDto(
        String countryISO2,
        String swiftCode,
        String codeType,
        String bankName,
        String address,
        String townName,
        String countryName,
        String timeZone) {

    /**
     * Converts the current DTO to a Headquarters object.
     *
     * @return A new Headquarters instance with the data from the DTO.
     */
    public Headquarters toHeadquarters() {
        return Headquarters
                .builder()
                .countryIso2(countryISO2)
                .swiftCode(swiftCode)
                .swiftPrefix(getPrefix())
                .codeType(codeType)
                .bankName(bankName)
                .address(address)
                .townName(townName)
                .countryName(countryName)
                .timeZone(timeZone)
                .build();
    }

    /**
     * Converts the current DTO to a Branch object.
     *
     * @return A new Branch instance with the data from the DTO.
     */
    public Branch toBranch() {
        return Branch
                .builder()
                .countryIso2(countryISO2)
                .swiftCode(swiftCode)
                .swiftPrefix(getPrefix())
                .codeType(codeType)
                .bankName(bankName)
                .address(address)
                .townName(townName)
                .countryName(countryName)
                .timeZone(timeZone)
                .build();
    }

    /**
     * Checks if the given SWIFT code corresponds to a headquarter.
     * A headquarter is identified by a SWIFT code ending with "XXX".
     *
     * @return True if the SWIFT code indicates a headquarter, false otherwise.
     */
    public boolean checkIfHeadquarter() {
        return swiftCode.endsWith("XXX");
    }

    /**
     * Extracts the SWIFT code prefix (first 8 characters) used to identify the bank.
     *
     * @return The prefix of the SWIFT code.
     */
    private String getPrefix() {
        return swiftCode.substring(0, 8);
    }
}
