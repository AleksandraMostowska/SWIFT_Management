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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class GetAllHeadquartersTest {

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
                .swiftCode("PLNXXX67890")
                .swiftPrefix("PLNXXX67")
                .townName("Town 2")
                .timeZone("TimeZone 2")
                .codeType("CodeType 2")
                .build();
    }

    @Test
    @DisplayName("Should return list of all headquarters when found")
    void shouldReturnListOfHeadquarters() {
        when(headquartersRepository.findAll()).thenReturn(List.of(headquarters1, headquarters2));

        var result = headquartersService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Bank 1", result.get(0).bankName());
        assertEquals("Bank 2", result.get(1).bankName());

        verify(headquartersRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no headquarters found")
    void shouldReturnEmptyListWhenNoHeadquartersFound() {
        when(headquartersRepository.findAll()).thenReturn(Collections.emptyList());

        var result = headquartersService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(headquartersRepository, times(1)).findAll();
    }
}
