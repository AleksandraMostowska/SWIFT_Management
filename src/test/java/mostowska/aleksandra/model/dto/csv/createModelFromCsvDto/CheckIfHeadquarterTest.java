package mostowska.aleksandra.model.dto.csv.createModelFromCsvDto;

import mostowska.aleksandra.model.dto.csv.CreateModelFromCsvDto;
import mostowska.aleksandra.model.impl.Headquarters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckIfHeadquarterTest {
    @Test
    @DisplayName("Should return true when swiftCode ends with 'XXX'")
    void shouldReturnTrueWhenSwiftCodeEndsWithXXX() {
        var modelDto = new CreateModelFromCsvDto(
                "PL",
                "PLNXXXXXXXX",
                "TestCodeType",
                "Test Bank",
                "Test Address",
                "Test Town",
                "POLAND",
                "CET"
        );

        assertTrue(modelDto.checkIfHeadquarter());
    }

    @Test
    @DisplayName("Should return false when swiftCode does not end with 'XXX'")
    void shouldReturnFalseWhenSwiftCodeDoesNotEndWithXXX() {
        var modelDto = new CreateModelFromCsvDto(
                "PL",
                "PLNXXX12345",
                "TestCodeType",
                "Test Bank",
                "Test Address",
                "Test Town",
                "POLAND",
                "CET"
        );

        assertFalse(modelDto.checkIfHeadquarter());
    }

    @Test
    @DisplayName("Should return false when swiftCode is empty")
    void shouldReturnFalseWhenSwiftCodeIsEmpty() {
        var modelDto = new CreateModelFromCsvDto(
                "PL",
                "",
                "TestCodeType",
                "Test Bank",
                "Test Address",
                "Test Town",
                "POLAND",
                "CET"
        );

        assertFalse(modelDto.checkIfHeadquarter());
    }
}
