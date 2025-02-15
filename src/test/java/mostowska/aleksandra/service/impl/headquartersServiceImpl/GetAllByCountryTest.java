package mostowska.aleksandra.service.impl.headquartersServiceImpl;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.impl.Headquarters;
import mostowska.aleksandra.repository.model.HeadquartersRepository;
import mostowska.aleksandra.service.impl.HeadquartersServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class GetAllByCountryTest {

    @Mock
    private HeadquartersRepository headquartersRepository;

    @InjectMocks
    private HeadquartersServiceImpl headquartersService;


    @Test
    @DisplayName("Should return list of headquarters when found")
    void shouldReturnListOfHeadquarters() {
        var headquarters = Headquarters.builder()
                .address("Test Address")
                .bankName("Test Bank")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(true)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Test Town")
                .timeZone("Test Timezone")
                .codeType("Test Codetype")
                .build();
        when(headquartersRepository.findAllForCountry("PL")).thenReturn(List.of(headquarters));

        var result = headquartersService.getAllByCountry("PL");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test Bank", result.get(0).bankName());

        verify(headquartersRepository, times(1)).findAllForCountry("PL");
    }

    @Test
    @DisplayName("Should return empty list when no headquarters found")
    void shouldReturnEmptyListWhenNoHeadquartersFound() {
        when(headquartersRepository.findAllForCountry("PL")).thenReturn(Collections.emptyList());

        var result = headquartersService.getAllByCountry("PL");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(headquartersRepository, times(1)).findAllForCountry("PL");
    }

    @Test
    @DisplayName("Should throw exception when countryIso2 is null")
    void shouldThrowExceptionWhenCountryIso2IsNull() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> headquartersService.getAllByCountry(null));

        assertEquals("Country name must be passed", exception.getMessage());
    }
}
