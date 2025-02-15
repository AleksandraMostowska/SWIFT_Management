package mostowska.aleksandra.model.dto.createModelDto;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.CreateModelDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class VerifyTest {

    private CreateModelDto createModelDto;

    @Test
    @DisplayName("Should throw exception when headquarter flag is incorrect for branch")
    void shouldThrowExceptionForIncorrectHeadquarterFlagForBranch() {
        createModelDto = new CreateModelDto("Test Address", "Test Bank", "PL",
                "POLAND", true, "PLNXXX12345");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> createModelDto.verify());
        assertEquals("Wrong input for branch. Please check SWIFT code and isHeadquarter",
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when headquarter flag is incorrect for headquarters")
    void shouldThrowExceptionForIncorrectHeadquarterFlagForHeadquarters() {
        createModelDto = new CreateModelDto("Test Address", "Test Bank", "PL",
                "POLAND", false, "PLNXXXXXXXX");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> createModelDto.verify());
        assertEquals("Wrong input for headquarter. Please check SWIFT code and isHeadquarter",
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when country ISO2 is incorrect")
    void shouldThrowExceptionForIncorrectCountryISO2() {
        createModelDto = new CreateModelDto("Test Address", "Test Bank", "POLAND",
                "POLAND", false, "PLNXXX12345");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> createModelDto.verify());
        assertEquals("Wrong input for country ISO2 code - should contain 2 letters.",
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when SWIFT code length is incorrect")
    void shouldThrowExceptionForIncorrectSwiftCode() {
        createModelDto = new CreateModelDto("Test Address", "Test Bank", "PL",
                "POLAND", false, "PLNXXX123456789");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> createModelDto.verify());
        assertEquals("Wrong input for SWIFT code - should contain 11 letters.",
                exception.getMessage());
    }

    @Test
    @DisplayName("Should pass verification when all values are correct")
    void shouldPassVerificationWhenDataIsCorrect() {
        createModelDto = new CreateModelDto("Test Address", "Test Bank", "PL",
                "POLAND", true, "PLNXXXXXXXX");
        assertDoesNotThrow(() -> createModelDto.verify());
    }
}

