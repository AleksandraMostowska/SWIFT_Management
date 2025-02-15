package mostowska.aleksandra.service.impl.headquartersServiceImpl;

import lombok.extern.slf4j.Slf4j;
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
public class GetAllBySwiftCodeTest {

    @Mock
    private HeadquartersRepository headquartersRepository;

    @InjectMocks
    private HeadquartersServiceImpl headquartersService;

    private Headquarters headquarters1;
    private Headquarters headquarters2;

    @BeforeEach
    void setUp() {
        headquarters1 = Headquarters.builder()
                .address("Address 1")
                .bankName("Bank 1")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(true)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Town 1")
                .timeZone("TimeZone 1")
                .codeType("CodeType 1")
                .build();

        headquarters2 = Headquarters.builder()
                .address("Address 2")
                .bankName("Bank 2")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(true)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Town 2")
                .timeZone("TimeZone 2")
                .codeType("CodeType 2")
                .build();
    }

    @Test
    @DisplayName("Should return list of headquarters for valid swiftCode")
    void shouldReturnListOfHeadquarters() {
        when(headquartersRepository.findAllForSWIFT("PLNXXX12345")).thenReturn(List.of(headquarters1, headquarters2));
        var result = headquartersService.getAllBySwiftCode("PLNXXX12345");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Address 1", result.get(0).address());
        assertEquals("Bank 1", result.get(0).bankName());
        assertEquals("Address 2", result.get(1).address());
        assertEquals("Bank 2", result.get(1).bankName());

        verify(headquartersRepository, times(1)).findAllForSWIFT("PLNXXX12345");
    }

    @Test
    @DisplayName("Should return empty list when no headquarters found for given swiftCode")
    void shouldReturnEmptyListWhenNoHeadquartersFound() {
        when(headquartersRepository.findAllForSWIFT("PLNXXX12345")).thenReturn(Collections.emptyList());
        var result = headquartersService.getAllBySwiftCode("PLNXXX12345");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(headquartersRepository, times(1)).findAllForSWIFT("PLNXXX12345");
    }
}
