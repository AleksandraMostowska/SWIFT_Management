package mostowska.aleksandra.service.impl.headquartersServiceImpl;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.headquarters.GetHeadquartersDto;
import mostowska.aleksandra.model.impl.Headquarters;
import mostowska.aleksandra.repository.model.HeadquartersRepository;
import mostowska.aleksandra.service.impl.HeadquartersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class RemoveHeadquartersTest {

    @Mock
    private HeadquartersRepository headquartersRepository;

    @InjectMocks
    private HeadquartersServiceImpl headquartersService;

    @Test
    @DisplayName("Should remove headquarters when swiftCode is valid")
    void shouldRemoveHeadquarters() {
        var headquartersToRemove = Headquarters
                .builder()
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

        when(headquartersRepository.findAllForSWIFT("PLNXXX12345")).thenReturn(List.of(headquartersToRemove));
        when(headquartersRepository.delete("PLNXXX12345")).thenReturn(headquartersToRemove);

        GetHeadquartersDto result = headquartersService.removeHeadquarters("PLNXXX12345");

        assertNotNull(result);
        assertEquals("Test Address", result.address());
        assertEquals("Test Bank", result.bankName());
        verify(headquartersRepository, times(1)).findAllForSWIFT("PLNXXX12345");
        verify(headquartersRepository, times(1)).delete("PLNXXX12345");
    }

    @Test
    @DisplayName("Should throw exception when swiftCode is null")
    void shouldThrowExceptionWhenSwiftCodeIsNull() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> headquartersService.removeHeadquarters(null));

        assertEquals("Removal failed", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when no headquarters found for given swiftCode")
    void shouldThrowExceptionWhenHeadquartersNotFound() {
        when(headquartersRepository.findAllForSWIFT("PLNXXX12345")).thenReturn(Collections.emptyList());

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> headquartersService.removeHeadquarters("PLNXXX12345"));

        assertEquals("Item not found", exception.getMessage());
        verify(headquartersRepository, times(1)).findAllForSWIFT("PLNXXX12345");
        verify(headquartersRepository, never()).delete(anyString());
    }
}
