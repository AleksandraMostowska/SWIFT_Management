package mostowska.aleksandra.service.impl.branchServiceImpl;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.branch.GetBranchDto;
import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.repository.model.BranchRepository;
import mostowska.aleksandra.service.impl.BranchServiceImpl;
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
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceImpl branchService;

    private Branch branch1;
    private Branch branch2;

    @BeforeEach
    void setUp() {
        branch1 = Branch.builder()
                .address("Address 1")
                .bankName("Bank 1")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(false)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Town 1")
                .timeZone("TimeZone 1")
                .codeType("CodeType 1")
                .build();

        branch2 = Branch.builder()
                .address("Address 2")
                .bankName("Bank 2")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(false)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Town 2")
                .timeZone("TimeZone 2")
                .codeType("CodeType 2")
                .build();
    }

    @Test
    @DisplayName("Should return list of branches for valid swiftCode")
    void shouldReturnListOfBranches() {
        when(branchRepository.findAllForSWIFT("PLNXXX12345")).thenReturn(List.of(branch1, branch2));
        var result = branchService.getAllBySwiftCode("PLNXXX12345");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Address 1", result.get(0).address());
        assertEquals("Bank 1", result.get(0).bankName());
        assertEquals("Address 2", result.get(1).address());
        assertEquals("Bank 2", result.get(1).bankName());

        verify(branchRepository, times(1)).findAllForSWIFT("PLNXXX12345");
    }

    @Test
    @DisplayName("Should return empty list when no branches found for given swiftCode")
    void shouldReturnEmptyListWhenNoBranchesFound() {
        when(branchRepository.findAllForSWIFT("PLNXXX12345")).thenReturn(Collections.emptyList());
        var result = branchService.getAllBySwiftCode("PLNXXX12345");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(branchRepository, times(1)).findAllForSWIFT("PLNXXX12345");
    }
}
