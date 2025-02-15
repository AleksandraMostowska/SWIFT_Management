package mostowska.aleksandra.model.dto.csv.createModelFromCsvDto;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.csv.CreateModelFromCsvDto;
import mostowska.aleksandra.model.impl.Headquarters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ToHeadquartersTest {

    private CreateModelFromCsvDto createModelFromCsvDto;

    @BeforeEach
    void setUp() {
        createModelFromCsvDto = new CreateModelFromCsvDto(
                "PL",
                "PLNXXXXXXXX",
                "TestCodeType",
                "Test Bank",
                "Test Address",
                "Test Town",
                "POLAND",
                "CET"
        );
    }

    @Test
    @DisplayName("When all data is correct, should return a valid Headquarters object")
    void shouldReturnValidHeadquarters() {
        var headquarters = createModelFromCsvDto.toHeadquarters();

        assertNotNull(headquarters);
        var expected = "Model{countryISO2='PL', swiftCode='PLNXXXXXXXX', swiftPrefix='PLNXXXXX', codeType='TestCodeType', " +
                "bankName='Test Bank', address='Test Address', townName='Test Town', countryName='POLAND', timeZone='CET', " +
                "isHeadquarter=null}";
        assertEquals(expected, headquarters.toString());
    }
}

