package mostowska.aleksandra.api.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ResponseDtoTest {

    @Test
    @DisplayName("Should create ResponseDto with data and no error")
    void shouldCreateResponseDtoWithDataOnly() {
        var response = new ResponseDto<>(100);
        assertNotNull(response);
        assertEquals(100, response.data());
        assertNull(response.error());
    }

    @Test
    @DisplayName("Should create ResponseDto with error and no data")
    void shouldCreateResponseDtoWithErrorOnly() {
        var expectedError = "Something went wrong";
        var response = new ResponseDto<>(expectedError);
        assertNotNull(response);
        assertEquals(expectedError, response.error());
        assertNull(response.data());
    }

    @Test
    @DisplayName("Should create ResponseDto with null data and null error")
    void shouldCreateEmptyResponseDto() {
        var response = new ResponseDto<>(null, null);
        assertNotNull(response);
        assertNull(response.data());
        assertNull(response.error());
    }
}

