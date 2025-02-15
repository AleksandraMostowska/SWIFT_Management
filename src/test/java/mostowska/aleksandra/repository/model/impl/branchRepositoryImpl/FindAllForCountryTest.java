package mostowska.aleksandra.repository.model.impl.branchRepositoryImpl;

import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.repository.model.impl.BranchRepositoryImpl;
import org.jdbi.v3.core.Jdbi;
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

    @InjectMocks
    private BranchRepositoryImpl branchRepository;

    private Branch branch1;
    private Branch branch2;

    @BeforeEach
    void setUp() {
        branch1 = Branch.builder()
                .address("Test Address 1")
                .bankName("Test Bank 1")
                .countryIso2("PL")
                .countryName("POLAND")
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Test Town 1")
                .timeZone("CET")
                .codeType("Test CodeType 1")
                .build();

        branch2 = Branch.builder()
                .address("Test Address 2")
                .bankName("Test Bank 2")
                .countryIso2("PL")
                .countryName("POLAND")
                .swiftCode("PLNXXX12346")
                .swiftPrefix("PLNXXX13")
                .townName("Test Town 2")
                .timeZone("CET")
                .codeType("Test CodeType 2")
                .build();
    }

    @Test
    @DisplayName("Should find all branches for a given country ISO2")
    void shouldFindAllBranchesForGivenCountryISO2() {
        String countryISO2 = "PL";
        var expectedBranches = List.of(branch1, branch2);

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(expectedBranches);

        var result = branchRepository.findAllForCountry(countryISO2);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Address 1", result.get(0).toGetBranchDto().address());
        assertEquals("Test Address 2", result.get(1).toGetBranchDto().address());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should return empty list if no branches found for country ISO2")
    void shouldReturnEmptyListIfNoBranchesFound() {
        var countryISO2 = "US";

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(List.of());

        var result = branchRepository.findAllForCountry(countryISO2);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should correctly query database for branches in the given country")
    void shouldCorrectlyQueryDatabaseForBranchesInGivenCountry() {
        var countryISO2 = "PL";
        var expectedBranches = List.of(branch1);

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(expectedBranches);

        var result = branchRepository.findAllForCountry(countryISO2);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Address 1", result.get(0).toGetBranchDto().address());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }
}

