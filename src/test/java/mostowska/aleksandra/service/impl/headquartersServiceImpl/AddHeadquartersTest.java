package mostowska.aleksandra.service.impl.headquartersServiceImpl;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.CreateModelDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AddHeadquartersTest {

    @Mock
    private HeadquartersRepository headquartersRepository;

    @InjectMocks
    private HeadquartersServiceImpl headquartersService;

    private CreateModelDto createModelDto;

    @BeforeEach
    void setUp() {
        createModelDto = new CreateModelDto(
                "Test Address",
                "Test Bank",
                "PL",
                "POLAND",
                false,
                "PLNXXX12345"
        );
    }

    @Test
    @DisplayName("When all data is correct")
    void shouldAddHeadquarters() {
        var savedHeadquarters = Headquarters
                .builder()
                .address("Test Address")
                .bankName("Test Bank")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(false)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Test Town")
                .timeZone("Test Timezone")
                .codeType("Test Codetype")
                .build();

        when(headquartersRepository.save(Mockito.any(Headquarters.class))).thenReturn(savedHeadquarters);

        GetHeadquartersDto result = headquartersService.addHeadquarters(createModelDto, "PLNXXX12345");

        assertNotNull(result);
        assertEquals("Test Address", result.address());
        assertEquals("Test Bank", result.bankName());
        assertEquals("POLAND", result.countryName());
        assertEquals("PL", result.countryISO2());
        assertEquals("PLNXXX12345", result.swiftCode());

        verify(headquartersRepository, times(1)).save(Mockito.any(Headquarters.class));
    }

    @Test
    @DisplayName("When passed model is null")
    void shouldThrowExceptionWhenAddHeadquartersIsNull() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> headquartersService.addHeadquarters(null, "someSwiftCode"));

        assertEquals("Item cannot be null", exception.getMessage());
    }
}
