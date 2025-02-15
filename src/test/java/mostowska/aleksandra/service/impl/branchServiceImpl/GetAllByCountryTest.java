package mostowska.aleksandra.service.impl.branchServiceImpl;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.country.GetModelForCountryDto;
import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.repository.model.BranchRepository;
import mostowska.aleksandra.service.impl.BranchServiceImpl;
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
public class GetAllByCountryTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceImpl branchService;


    @Test
    @DisplayName("Should return list of branches when found")
    void shouldReturnListOfBranches() {
        var branch = Branch.builder()
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
        when(branchRepository.findAllForCountry("PL")).thenReturn(List.of(branch));

        var result = branchService.getAllByCountry("PL");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test Bank", result.get(0).bankName());

        verify(branchRepository, times(1)).findAllForCountry("PL");
    }

    @Test
    @DisplayName("Should return empty list when no branches found")
    void shouldReturnEmptyListWhenNoBranchesFound() {
        when(branchRepository.findAllForCountry("PL")).thenReturn(Collections.emptyList());

        var result = branchService.getAllByCountry("PL");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(branchRepository, times(1)).findAllForCountry("PL");
    }

    @Test
    @DisplayName("Should throw exception when countryIso2 is null")
    void shouldThrowExceptionWhenCountryIso2IsNull() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> branchService.getAllByCountry(null));

        assertEquals("Country name must be passed", exception.getMessage());
    }
}
