package mostowska.aleksandra.model.dto.csv.createModelFromCsvDto;

import mostowska.aleksandra.model.dto.csv.CreateModelFromCsvDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToBranchTest {
    private CreateModelFromCsvDto createModelFromCsvDto;

    @BeforeEach
    void setUp() {
        createModelFromCsvDto = new CreateModelFromCsvDto(
                "PL",
                "PLNXXXXX123",
                "TestCodeType",
                "Test Bank",
                "Test Address",
                "Test Town",
                "POLAND",
                "CET"
        );
    }

    @Test
    @DisplayName("When all data is correct, should return a valid Branch object")
    void shouldReturnValidBranch() {
        var branch = createModelFromCsvDto.toBranch();

        assertNotNull(branch);
        var expected = "Model{countryISO2='PL', swiftCode='PLNXXXXX123', swiftPrefix='PLNXXXXX', codeType='TestCodeType', " +
                "bankName='Test Bank', address='Test Address', townName='Test Town', countryName='POLAND', timeZone='CET', " +
                "isHeadquarter=null}";
        assertEquals(expected, branch.toString());
    }
}
