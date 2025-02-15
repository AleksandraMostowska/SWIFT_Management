package mostowska.aleksandra.service.impl.branchServiceImpl;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.CreateModelDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AddBranchTest {
    @Mock
    private BranchRepository branchRepository;
    @InjectMocks
    private BranchServiceImpl branchService;
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
    void shouldAddBranch() {
        var savedBranch = Branch
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

        when(branchRepository.save(Mockito.any(Branch.class))).thenReturn(savedBranch);
        var result = branchService.addBranch(createModelDto, "PLNXXX12345");

        assertNotNull(result);
        assertEquals("Test Address", result.address());
        assertEquals("Test Bank", result.bankName());
        assertEquals("POLAND", result.countryName());
        assertEquals("PL", result.countryISO2());
        assertEquals("PLNXXX12345", result.swiftCode());
        verify(branchRepository, times(1)).save(Mockito.any(Branch.class));

    }

    @Test
    @DisplayName("When passed model is null")
    void shouldThrowExceptionWhenAddBranchIsNull() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> branchService.addBranch(null, "someSwiftCode"));
        assertEquals("Item cannot be null", exception.getMessage());
    }
}
