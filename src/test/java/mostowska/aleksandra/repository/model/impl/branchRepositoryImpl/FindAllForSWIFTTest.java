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
class FindAllForSWIFTTest {

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
                .swiftPrefix("PLNXXX12")
                .townName("Test Town 2")
                .timeZone("CET")
                .codeType("Test CodeType 2")
                .build();
    }

    @Test
    @DisplayName("Should find all branches for a given SWIFT code prefix")
    void shouldFindAllBranchesForGivenSWIFTCode() {
        var swiftCode = "PLNXXX12345";
        var expectedBranches = List.of(branch1, branch2);

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(expectedBranches);

        var result = branchRepository.findAllForSWIFT(swiftCode);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Address 1", result.get(0).toGetBranchDto().address());
        assertEquals("Test Address 2", result.get(1).toGetBranchDto().address());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should return empty list if no branches found for SWIFT code prefix")
    void shouldReturnEmptyListIfNoBranchesFound() {
        var swiftCode = "PLNXXX12347";

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(List.of());

        var result = branchRepository.findAllForSWIFT(swiftCode);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should correctly extract swift_prefix from SWIFT code and query database")
    void shouldCorrectlyExtractSwiftPrefixFromSWIFTCode() {
        var swiftCode = "PLNXXX12345";
        var expectedBranches = List.of(branch1);

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(expectedBranches);

        var result = branchRepository.findAllForSWIFT(swiftCode);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Address 1", result.get(0).toGetBranchDto().address());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }
}
