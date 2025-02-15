package mostowska.aleksandra.repository.generic.abstractCrudRepository;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.repository.model.impl.BranchRepositoryImpl;
import org.jdbi.v3.core.Handle;
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

@Slf4j
@ExtendWith(MockitoExtension.class)
class FindAllBySwiftCodeTest {

    @Mock
    private Jdbi jdbi;

    @Mock
    private Handle handle;

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
                .isHeadquarter(false)
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
                .isHeadquarter(false)
                .swiftCode("PLNXXX12346")
                .swiftPrefix("PLNXXX13")
                .townName("Test Town 2")
                .timeZone("CET")
                .codeType("Test CodeType 2")
                .build();
    }

    @Test
    @DisplayName("Should find all branches by swiftCodes successfully")
    void shouldFindAllBranchesBySwiftCodesSuccessfully() {
        var swiftCodes = List.of("PLNXXX12345", "PLNXXX12346");

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(List.of(branch1, branch2));

        var result = branchRepository.findAllBySwiftCode(swiftCodes);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Address 1", result.get(0).toGetBranchDto().address());
        assertEquals("Test Address 2", result.get(1).toGetBranchDto().address());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should throw exception when not all swiftCodes are found")
    void shouldThrowExceptionWhenNotAllSwiftCodesAreFound() {
        var swiftCodes = List.of("PLNXXX12345", "PLNXXX12347");

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(List.of(branch1));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> branchRepository.findAllBySwiftCode(swiftCodes));

        assertEquals("Not all swiftCodes are present in table", exception.getMessage());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should return empty list if no branches found")
    void shouldReturnEmptyListIfNoBranchesFound() {
        var swiftCodes = List.of("PLNXXX12345", "PLNXXX12346");

        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(List.of());

        var result = branchRepository.findAllBySwiftCode(swiftCodes);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(jdbi, times(1)).withHandle(Mockito.any());
    }
}

