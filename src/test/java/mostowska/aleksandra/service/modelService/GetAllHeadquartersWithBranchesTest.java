package mostowska.aleksandra.service.modelService;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.GetModelDto;
import mostowska.aleksandra.model.dto.branch.GetBranchDto;
import mostowska.aleksandra.model.dto.headquarters.GetHeadquartersDto;
import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.model.impl.Headquarters;
import mostowska.aleksandra.service.ModelService;
import mostowska.aleksandra.service.impl.BranchServiceImpl;
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
public class GetAllHeadquartersWithBranchesTest {

    @Mock
    private HeadquartersServiceImpl headquartersService;

    @Mock
    private BranchServiceImpl branchService;

    @InjectMocks
    private ModelService modelService;

    private GetBranchDto getBranchDto;
    private GetHeadquartersDto getHeadquartersDto;
    private final String SWIFT_CODE_XXX = "PLNXXXXXXXX";
    private final String SWIFT_CODE_NORMAL = "PLNABC12345";

    @BeforeEach
    void setUp() {
        getBranchDto = Branch.builder()
                .address("Branch Address")
                .bankName("Test Bank")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(false)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Test Town")
                .timeZone("Test Timezone")
                .codeType("Test Codetype")
                .build()
                .toGetBranchDto();

        getHeadquartersDto = Headquarters.builder()
                .address("Headquarters Address")
                .bankName("Test Bank")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(true)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("HQ Town")
                .timeZone("HQ Timezone")
                .codeType("HQ Codetype")
                .build()
                .toGetHeadquartersDto();
    }

    @Test
    @DisplayName("Should return headquarters with branches when swiftCode ends with XXX")
    void shouldReturnHeadquartersWithBranchesWhenSwiftEndsWithXXX() {
        when(headquartersService.getAllBySwiftCode(SWIFT_CODE_XXX))
                .thenReturn(List.of(getHeadquartersDto));
        when(branchService.getAllBySwiftCode(SWIFT_CODE_XXX))
                .thenReturn(List.of(getBranchDto));

        var result = modelService.getAllHeadquartersWithBranches(SWIFT_CODE_XXX);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof GetHeadquartersDto);

        verify(headquartersService, times(1)).getAllBySwiftCode(SWIFT_CODE_XXX);
        verify(branchService, times(1)).getAllBySwiftCode(SWIFT_CODE_XXX);
    }

    @Test
    @DisplayName("Should return only branches when swiftCode does not end with XXX")
    void shouldReturnOnlyBranchesWhenSwiftCodeDoesNotEndWithXXX() {
        when(branchService.getAllBySwiftCode(SWIFT_CODE_NORMAL))
                .thenReturn(List.of(getBranchDto));

        var result = modelService.getAllHeadquartersWithBranches(SWIFT_CODE_NORMAL);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof GetBranchDto);

        verify(headquartersService, never()).getAllBySwiftCode(anyString());
        verify(branchService, times(1)).getAllBySwiftCode(SWIFT_CODE_NORMAL);
    }

    @Test
    @DisplayName("Should return empty list when no headquarters and branches are found for XXX swift code")
    void shouldReturnEmptyListWhenNoHeadquartersAndBranchesFoundForXXX() {
        when(headquartersService.getAllBySwiftCode(SWIFT_CODE_XXX)).thenReturn(Collections.emptyList());
        when(branchService.getAllBySwiftCode(SWIFT_CODE_XXX)).thenReturn(Collections.emptyList());

        var result = modelService.getAllHeadquartersWithBranches(SWIFT_CODE_XXX);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(headquartersService, times(1)).getAllBySwiftCode(SWIFT_CODE_XXX);
        verify(branchService, times(1)).getAllBySwiftCode(SWIFT_CODE_XXX);
    }

    @Test
    @DisplayName("Should return empty list when no branches are found for non-XXX swift code")
    void shouldReturnEmptyListWhenNoBranchesFoundForNonXXXSwiftCode() {
        when(branchService.getAllBySwiftCode(SWIFT_CODE_NORMAL)).thenReturn(Collections.emptyList());

        var result = modelService.getAllHeadquartersWithBranches(SWIFT_CODE_NORMAL);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(headquartersService, never()).getAllBySwiftCode(anyString());
        verify(branchService, times(1)).getAllBySwiftCode(SWIFT_CODE_NORMAL);
    }
}

