package mostowska.aleksandra.repository.utils;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.repository.Utils;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FindCountryNameByISO2Test {

    @Mock
    private Jdbi jdbi;

    @Mock
    private Handle handle;

    @InjectMocks
    private Utils utils;


    @Test
    @DisplayName("Should find country name from headquarters")
    void shouldFindCountryNameFromHeadquarters() {
        var countryIso2 = "PL";

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(Optional.of("Poland"));

        var result = utils.findCountryNameByISO2(countryIso2);

        assertNotNull(result);
        assertEquals("Poland", result);

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should find country name from branches when not found in headquarters")
    void shouldFindCountryNameFromBranchesWhenNotFoundInHeadquarters() {
        var countryIso2 = "PL";

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(Optional.empty());

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(Optional.of("Poland"));

        var result = utils.findCountryNameByISO2(countryIso2);

        assertNotNull(result);
        assertEquals("Poland", result);

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should throw exception when country is not found in both tables")
    void shouldThrowExceptionWhenCountryIsNotFoundInBothTables() {
        var countryIso2 = "XX";

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> utils.findCountryNameByISO2(countryIso2));

        assertEquals("No such country", exception.getMessage());

        verify(jdbi, times(2)).withHandle(Mockito.any());
    }
}

