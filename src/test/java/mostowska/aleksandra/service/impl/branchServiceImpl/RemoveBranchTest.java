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
public class RemoveBranchTest {
    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceImpl branchService;


    @Test
    @DisplayName("Should remove branch when swiftCode is valid")
    void shouldRemoveBranch() {
        var branchToRemove = Branch
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

        when(branchRepository.findAllForSWIFT("PLNXXX12345")).thenReturn(List.of(branchToRemove));
        when(branchRepository.delete("PLNXXX12345")).thenReturn(branchToRemove);

        var result = branchService.removeBranch("PLNXXX12345");

        assertNotNull(result);
        assertEquals("Test Address", result.address());
        assertEquals("Test Bank", result.bankName());
        verify(branchRepository, times(1)).findAllForSWIFT("PLNXXX12345");
        verify(branchRepository, times(1)).delete("PLNXXX12345");
    }

    @Test
    @DisplayName("Should throw exception when swiftCode is null")
    void shouldThrowExceptionWhenSwiftCodeIsNull() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> branchService.removeBranch(null));

        assertEquals("Removal failed", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when no branch found for given swiftCode")
    void shouldThrowExceptionWhenBranchNotFound() {
        when(branchRepository.findAllForSWIFT("PLNXXX12345")).thenReturn(Collections.emptyList());

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> branchService.removeBranch("PLNXXX12345"));

        assertEquals("Item not found", exception.getMessage());
        verify(branchRepository, times(1)).findAllForSWIFT("PLNXXX12345");
        verify(branchRepository, never()).delete(anyString());
    }
}
