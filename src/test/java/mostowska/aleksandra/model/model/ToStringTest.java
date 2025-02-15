package mostowska.aleksandra.model.model;

import mostowska.aleksandra.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ToStringTest {
    private Model model;

    @BeforeEach
    void setUp() {
        model = new TestModel(
                "PL",
                "PLNXXX12345",
                "PLNXXX12",
                "TestCodeType",
                "Test Bank",
                "Test Address",
                "Test Town",
                "POLAND",
                "CET",
                false
        );
    }

    @Test
    @DisplayName("toString should return correct string representation of the Model")
    void shouldReturnCorrectToString() {
        String expected = "Model{" +
                "countryISO2='PL', " +
                "swiftCode='PLNXXX12345', " +
                "swiftPrefix='PLNXXX12', " +
                "codeType='TestCodeType', " +
                "bankName='Test Bank', " +
                "address='Test Address', " +
                "townName='Test Town', " +
                "countryName='POLAND', " +
                "timeZone='CET', " +
                "isHeadquarter=false" +
                "}";

        assertEquals(expected, model.toString());
    }

    private static class TestModel extends Model {
        public TestModel(String countryIso2, String swiftCode, String swiftPrefix, String codeType, String bankName,
                         String address, String townName, String countryName, String timeZone, Boolean isHeadquarter) {
            super(countryIso2, swiftCode, swiftPrefix, codeType, bankName, address, townName, countryName, timeZone, isHeadquarter);
        }
    }
}
