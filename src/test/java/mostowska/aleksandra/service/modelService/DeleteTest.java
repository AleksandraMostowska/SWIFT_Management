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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class DeleteTest {

    @Mock
    private HeadquartersServiceImpl headquartersService;

    @Mock
    private BranchServiceImpl branchService;

    @InjectMocks
    private ModelService modelService;

    private GetHeadquartersDto headquartersDto;
    private GetBranchDto branchDto;

    @BeforeEach
    void setUp() {
        branchDto = Branch
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
                .build()
                .toGetBranchDto();

        headquartersDto = Headquarters
                .builder()
                .address("Test Address")
                .bankName("Test Bank")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(true)
                .swiftCode("PLNXXXXXXXX")
                .swiftPrefix("PLNXXXXX")
                .townName("Test Town")
                .timeZone("Test Timezone")
                .codeType("Test Codetype")
                .build()
                .toGetHeadquartersDto();
    }

    @Test
    @DisplayName("Should delete headquarters when swiftCode ends with XXX")
    void shouldDeleteHeadquartersWhenSwiftEndsWithXXX() {
        String SWIFT_CODE_XXX = "PLNXXXXXXXX";
        when(headquartersService.removeHeadquarters(SWIFT_CODE_XXX))
                .thenReturn(headquartersDto);

        var result = modelService.delete(SWIFT_CODE_XXX);
        log.info(result.toString());

        assertNotNull(result);

        verify(headquartersService, times(1)).removeHeadquarters(SWIFT_CODE_XXX);
        verify(branchService, never()).removeBranch(anyString());
    }

    @Test
    @DisplayName("Should delete branch when swiftCode does not end with XXX")
    void shouldDeleteBranchWhenSwiftCodeDoesNotEndWithXXX() {
        String SWIFT_CODE_NORMAL = "PLNABC12345";
        when(branchService.removeBranch(SWIFT_CODE_NORMAL)).thenReturn(branchDto);

        var result = modelService.delete(SWIFT_CODE_NORMAL);

        assertNotNull(result);

        verify(branchService, times(1)).removeBranch(SWIFT_CODE_NORMAL);
        verify(headquartersService, never()).removeHeadquarters(anyString());
    }

}

