package mostowska.aleksandra.service.modelService;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.CreateModelDto;
import mostowska.aleksandra.model.dto.branch.GetBranchDto;
import mostowska.aleksandra.model.dto.headquarters.GetHeadquartersDto;
import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.model.impl.Headquarters;
import mostowska.aleksandra.service.ModelService;
import mostowska.aleksandra.service.impl.HeadquartersServiceImpl;
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
public class SaveModelToDBTest {

    @Mock
    private HeadquartersServiceImpl headquartersService;

    @Mock
    private BranchServiceImpl branchService;

    @InjectMocks
    private ModelService modelService;

    private CreateModelDto createModelDto;


    @Test
    @DisplayName("Should save branch when isHeadquarter is false")
    void shouldSaveBranchWhenHeadquarterIsFalse() {
        createModelDto = new CreateModelDto(
                "Test Address",
                "Test Bank",
                "PL",
                "POLAND",
                false,
                "PLNXXX12345"
        );

        var branch = Branch
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
        var getBranchDto = branch.toGetBranchDto();

        when(branchService.addBranch(any(CreateModelDto.class), anyString()))
                .thenReturn(getBranchDto);

        var result = modelService.saveModelToDB(createModelDto);


        assertNotNull(result);
        assertTrue(result instanceof GetBranchDto);

        verify(branchService, times(1))
                .addBranch(any(CreateModelDto.class), anyString());
        verify(headquartersService, never())
                .addHeadquarters(any(CreateModelDto.class), anyString());
    }

    @Test
    @DisplayName("Should save headquarters when isHeadquarter is true")
    void shouldSaveHeadquartersWhenHeadquarterIsTrue() {
        createModelDto = new CreateModelDto(
                "Test Address",
                "Test Bank",
                "PL",
                "POLAND",
                false,
                "PLNXXXXXXXX"
        );

        var headquarters = Headquarters
                .builder()
                .address("Test Address")
                .bankName("Test Bank")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(true)
                .swiftCode("PLNXXXXXXX")
                .swiftPrefix("PLNXXXXX")
                .townName("Test Town")
                .timeZone("Test Timezone")
                .codeType("Test Codetype")
                .build();

        var getHeadquartersDto = headquarters.toGetHeadquartersDto();

        when(headquartersService.addHeadquarters(any(CreateModelDto.class), anyString()))
                .thenReturn(getHeadquartersDto);

        var result = modelService.saveModelToDB(createModelDto);
        log.info(result.toString());

        assertNotNull(result);
        assertTrue(result instanceof GetHeadquartersDto);

        verify(headquartersService, times(1)).addHeadquarters(any(CreateModelDto.class), anyString());
        verify(branchService, never()).addBranch(any(CreateModelDto.class), anyString());
    }

    @Test
    @DisplayName("Should throw exception when CreateModelDto is null")
    void shouldThrowExceptionWhenCreateModelDtoIsNull() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> modelService.saveModelToDB(null));

        assertEquals("All required parameters must be passed in form:" +
                "{\n" +
                "    \"address\": string,\n" +
                "    \"bankName\": string,\n" +
                "    \"countryISO2\": string,\n" +
                "    \"countryName\": string,\n" +
                "    \"isHeadquarter\": bool,\n" +
                "    \"swiftCode\": string,\n" +
                "}\n", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when required fields are missing")
    void shouldThrowExceptionWhenRequiredFieldsAreMissing() {
        var invalidCreateModelDto = new CreateModelDto(
                null,
                "Test Bank",
                "PL",
                "POLAND",
                false,
                "PLNXXX12345"
        );

        var exception = assertThrows(IllegalArgumentException.class,
                () -> modelService.saveModelToDB(invalidCreateModelDto));

        assertEquals("Missing required field: address", exception.getMessage());
    }
}

