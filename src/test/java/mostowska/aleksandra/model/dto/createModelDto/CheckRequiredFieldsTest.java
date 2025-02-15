package mostowska.aleksandra.model.dto.createModelDto;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.CreateModelDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CheckRequiredFieldsTest {

    @Test
    @DisplayName("Should throw exception when address is missing")
    void shouldThrowExceptionWhenAddressIsMissing() {
        var createModelDto = new CreateModelDto(
                null, "Test Bank", "PL", "POLAND", false, "PLNXXX12345"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> CreateModelDto.checkRequiredFields(createModelDto));

        assertTrue(exception.getMessage().contains("address"));
    }

    @Test
    @DisplayName("Should throw exception when bank name is missing")
    void shouldThrowExceptionWhenBankNameIsMissing() {
        var createModelDto = new CreateModelDto(
                "Test Address", "", "PL", "POLAND", false, "PLNXXX12345"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> CreateModelDto.checkRequiredFields(createModelDto));

        assertTrue(exception.getMessage().contains("bankName"));
    }

    @Test
    @DisplayName("Should throw exception when country ISO2 is missing")
    void shouldThrowExceptionWhenCountryISO2IsMissing() {
        var createModelDto = new CreateModelDto(
                "Test Address", "Test Bank", null, "POLAND", false, "PLNXXX12345"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> CreateModelDto.checkRequiredFields(createModelDto));

        assertTrue(exception.getMessage().contains("countryISO2"));
    }

    @Test
    @DisplayName("Should throw exception when SWIFT code is missing")
    void shouldThrowExceptionWhenSwiftCodeIsMissing() {
        var createModelDto = new CreateModelDto(
                "Test Address", "Test Bank", "PL", "POLAND", false, ""
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> CreateModelDto.checkRequiredFields(createModelDto));

        assertTrue(exception.getMessage().contains("swiftCode"));
    }

    @Test
    @DisplayName("Should pass verification when all values are correct")
    void shouldPassVerificationWhenDataIsCorrect() {
        var createModelDto = new CreateModelDto(
                "Test Address", "Test Bank", "PL", "POLAND", false, "PLNXXX12345"
        );

        assertDoesNotThrow(() -> CreateModelDto.checkRequiredFields(createModelDto));
    }
}
