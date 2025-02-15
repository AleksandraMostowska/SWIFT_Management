package mostowska.aleksandra.model.dto;

import lombok.SneakyThrows;
import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.model.impl.Headquarters;

import java.lang.reflect.Field;
import java.util.Arrays;


/**
 * CreateModelDto is a data transfer object (DTO) representing the input data required
 * for creating a bank's branch or headquarter model.
 */
public record CreateModelDto(
    String address,
    String bankName,
    String countryISO2,
    String countryName,
    Boolean isHeadquarter,
    String swiftCode) {

    /**
     * Converts the current DTO to a Headquarters model.
     *
     * @return A new Headquarters instance populated with data from the DTO.
     */
    public Headquarters toHeadquarters() {
        return Headquarters
                .builder()
                .countryIso2(countryISO2.toUpperCase())
                .swiftCode(swiftCode)
                .swiftPrefix(getPrefix())
                .bankName(bankName)
                .address(address)
                .countryName(countryName.toUpperCase())
                .isHeadquarter(isHeadquarter)
                .build();
    }

    /**
     * Converts the current DTO to a Branch model.
     *
     * @return A new Branch instance populated with data from the DTO.
     */
    public Branch toBranch() {
        return Branch
                .builder()
                .countryIso2(countryISO2.toUpperCase())
                .swiftCode(swiftCode)
                .swiftPrefix(getPrefix())
                .bankName(bankName)
                .address(address)
                .countryName(countryName.toUpperCase())
                .isHeadquarter(isHeadquarter)
                .build();
    }

    /**
     * Checks if the given SWIFT code corresponds to a headquarter.
     * A headquarter is identified by a SWIFT code that ends with "XXX".
     *
     * @return True if the SWIFT code indicates a headquarter, false otherwise.
     */
    public boolean checkIfHeadquarter() {
        return swiftCode.endsWith("XXX");
    }

    /**
     * Verifies the correctness of the fields in the DTO.
     * Ensures that the SWIFT code and isHeadquarter flag are consistent,
     * that the countryISO2 code is valid, and that the SWIFT code has the correct length.
     */
    public void verify() {
        if (checkIfHeadquarter() && !isHeadquarter()) {
            throw new IllegalStateException("Wrong input for headquarter. Please check SWIFT code and isHeadquarter");
        }

        if (!checkIfHeadquarter() && isHeadquarter()) {
            throw new IllegalStateException("Wrong input for branch. Please check SWIFT code and isHeadquarter");
        }

        if (countryISO2.length() != 2) {
            throw new IllegalStateException("Wrong input for country ISO2 code - should contain 2 letters.");
        }

        if (swiftCode.length() != 11) {
            throw new IllegalStateException("Wrong input for SWIFT code - should contain 11 letters.");
        }

    }

    /**
     * Extracts the SWIFT code prefix (first 8 characters).
     *
     * @return The prefix of the SWIFT code.
     */
    private String getPrefix() {
            return swiftCode.substring(0, 8);
        }

    /**
     * Checks if any required field in the CreateModelDto is missing or empty.
     *
     * @param createModelDto The DTO instance to be validated.
     * @throws IllegalArgumentException if any required field is missing or empty.
     */
    public static void checkRequiredFields(CreateModelDto createModelDto) {
        Arrays.stream(CreateModelDto.class.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> CreateModelDto.isFieldEmpty(field, createModelDto))
                .findFirst()
                .ifPresent(field -> {
                    throw new IllegalArgumentException("Missing required field: " + field.getName());
                });
    }

    /**
     * Checks if a field in CreateModelDto is empty or null.
     *
     * @param field The field to check.
     * @param createModelDto The DTO instance.
     * @return True if the field is empty or null, false otherwise.
     * @throws IllegalAccessException if the field's value cannot be accessed.
     */
    @SneakyThrows
    public static boolean isFieldEmpty(Field field, CreateModelDto createModelDto) {
        Object value = field.get(createModelDto);
        return value == null || (value instanceof String && ((String) value).isEmpty());
    }
}
