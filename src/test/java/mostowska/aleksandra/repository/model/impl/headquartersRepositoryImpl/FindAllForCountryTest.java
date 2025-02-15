package mostowska.aleksandra.repository.model.impl.headquartersRepositoryImpl;

import mostowska.aleksandra.model.impl.Headquarters;
import mostowska.aleksandra.repository.model.impl.HeadquartersRepositoryImpl;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllForCountryTest {

    @Mock
    private Jdbi jdbi;

    @Mock
    private Handle handle;

    @Mock
    private Query query;

    @InjectMocks
    private HeadquartersRepositoryImpl headquartersRepository;

    private Headquarters headquarters1;
    private Headquarters headquarters2;

    @BeforeEach
    void setUp() {
        headquarters1 = Headquarters.builder()
                .address("Test Address 1")
                .bankName("Test Bank 1")
                .countryIso2("PL")
                .countryName("POLAND")
                .swiftCode("PLNXXX12XXX")
                .swiftPrefix("PLNXXX12")
                .townName("Test Town 1")
                .timeZone("CET")
                .codeType("Test CodeType 1")
                .build();

        headquarters2 = Headquarters.builder()
                .address("Test Address 2")
                .bankName("Test Bank 2")
                .countryIso2("PL")
                .countryName("POLAND")
                .swiftCode("PLNXXX21XXX")
                .swiftPrefix("PLNXXX21")
                .townName("Test Town 2")
                .timeZone("CET")
                .codeType("Test CodeType 2")
                .build();
    }

    @Test
    @DisplayName("Should find all headquarters for a given country ISO2")
    void shouldFindAllHeadquartersForGivenCountryISO2() {
        var countryISO2 = "PL";
        var expectedHeadquarters = List.of(headquarters1, headquarters2);

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(expectedHeadquarters);

        var result = headquartersRepository.findAllForCountry(countryISO2);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Address 1", result.get(0).toGetHeadquartersDto().address());
        assertEquals("Test Address 2", result.get(1).toGetHeadquartersDto().address());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should return empty list if no headquarters found for country ISO2")
    void shouldReturnEmptyListIfNoHeadquartersFound() {
        var countryISO2 = "US";

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(List.of());

        var result = headquartersRepository.findAllForCountry(countryISO2);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should correctly query database for branches in the given country")
    void shouldCorrectlyQueryDatabaseForHeadquartersInGivenCountry() {
        var countryISO2 = "PL";
        var expectedHeadquarters = List.of(headquarters1);

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(expectedHeadquarters);

        var result = headquartersRepository.findAllForCountry(countryISO2);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Address 1", result.get(0).toGetHeadquartersDto().address());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }
}
