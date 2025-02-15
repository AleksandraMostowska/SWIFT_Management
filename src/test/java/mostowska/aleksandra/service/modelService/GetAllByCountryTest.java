package mostowska.aleksandra.service.modelService;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.dto.country.GetGroupedByCountryDto;
import mostowska.aleksandra.model.dto.country.GetModelForCountryDto;
import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.model.impl.Headquarters;
import mostowska.aleksandra.repository.Utils;
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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class GetAllByCountryTest {

    @Mock
    private HeadquartersServiceImpl headquartersService;

    @Mock
    private BranchServiceImpl branchService;

    @Mock
    private Utils utilsRepository;

    @InjectMocks
    private ModelService modelService;

    private GetModelForCountryDto getBranchDto;
    private GetModelForCountryDto getHeadquartersDto;
    private final String COUNTRY_ISO2 = "PL";
    private final String COUNTRY_NAME = "POLAND";

    @BeforeEach
    void setUp() {
        getBranchDto = Branch.builder()
                .address("Branch Address")
                .bankName("Test Bank")
                .countryIso2(COUNTRY_ISO2)
                .countryName(COUNTRY_NAME)
                .isHeadquarter(false)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Test Town")
                .timeZone("Test Timezone")
                .codeType("Test Codetype")
                .build()
                .toGetModelByCountryDto();

        getHeadquartersDto = Headquarters.builder()
                .address("Headquarters Address")
                .bankName("Test Bank")
                .countryIso2(COUNTRY_ISO2)
                .countryName(COUNTRY_NAME)
                .isHeadquarter(true)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("HQ Town")
                .timeZone("HQ Timezone")
                .codeType("HQ Codetype")
                .build()
                .toGetModelByCountryDto();
    }

    @Test
    @DisplayName("Should return headquarters and branches for given country")
    void shouldReturnHeadquartersAndBranchesForGivenCountry() {
        when(headquartersService.getAllByCountry(COUNTRY_ISO2))
                .thenReturn(List.of(getHeadquartersDto));
        when(branchService.getAllByCountry(COUNTRY_ISO2))
                .thenReturn(List.of(getBranchDto));
        when(utilsRepository.findCountryNameByISO2(COUNTRY_ISO2)).thenReturn(COUNTRY_NAME);

        var result = modelService.getAllByCountry(COUNTRY_ISO2);

        assertNotNull(result);
        assertEquals(COUNTRY_ISO2, result.countryISO2());
        assertEquals(COUNTRY_NAME, result.countryName());
        assertEquals(2, result.items().size());

        verify(headquartersService, times(1)).getAllByCountry(COUNTRY_ISO2);
        verify(branchService, times(1)).getAllByCountry(COUNTRY_ISO2);
        verify(utilsRepository, times(1)).findCountryNameByISO2(COUNTRY_ISO2);
    }

    @Test
    @DisplayName("Should return only headquarters when no branches are found")
    void shouldReturnOnlyHeadquartersWhenNoBranchesFound() {
        when(headquartersService.getAllByCountry(COUNTRY_ISO2)).thenReturn(List.of(getHeadquartersDto));
        when(branchService.getAllByCountry(COUNTRY_ISO2)).thenReturn(Collections.emptyList());
        when(utilsRepository.findCountryNameByISO2(COUNTRY_ISO2)).thenReturn(COUNTRY_NAME);

        var result = modelService.getAllByCountry(COUNTRY_ISO2);

        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertNotNull(result.items().get(0));

        verify(headquartersService, times(1)).getAllByCountry(COUNTRY_ISO2);
        verify(branchService, times(1)).getAllByCountry(COUNTRY_ISO2);
        verify(utilsRepository, times(1)).findCountryNameByISO2(COUNTRY_ISO2);
    }

    @Test
    @DisplayName("Should return only branches when no headquarters are found")
    void shouldReturnOnlyBranchesWhenNoHeadquartersFound() {
        when(headquartersService.getAllByCountry(COUNTRY_ISO2)).thenReturn(Collections.emptyList());
        when(branchService.getAllByCountry(COUNTRY_ISO2)).thenReturn(List.of(getBranchDto));
        when(utilsRepository.findCountryNameByISO2(COUNTRY_ISO2)).thenReturn(COUNTRY_NAME);

        var result = modelService.getAllByCountry(COUNTRY_ISO2);

        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertNotNull(result.items().get(0));

        verify(headquartersService, times(1)).getAllByCountry(COUNTRY_ISO2);
        verify(branchService, times(1)).getAllByCountry(COUNTRY_ISO2);
        verify(utilsRepository, times(1)).findCountryNameByISO2(COUNTRY_ISO2);
    }

    @Test
    @DisplayName("Should return empty list when no branches and headquarters are found")
    void shouldReturnEmptyListWhenNoBranchesAndHeadquartersFound() {
        when(headquartersService.getAllByCountry(COUNTRY_ISO2)).thenReturn(Collections.emptyList());
        when(branchService.getAllByCountry(COUNTRY_ISO2)).thenReturn(Collections.emptyList());
        when(utilsRepository.findCountryNameByISO2(COUNTRY_ISO2)).thenReturn(COUNTRY_NAME);

        var result = modelService.getAllByCountry(COUNTRY_ISO2);

        assertNotNull(result);
        assertEquals(0, result.items().size());

        verify(headquartersService, times(1)).getAllByCountry(COUNTRY_ISO2);
        verify(branchService, times(1)).getAllByCountry(COUNTRY_ISO2);
        verify(utilsRepository, times(1)).findCountryNameByISO2(COUNTRY_ISO2);
    }

    @Test
    @DisplayName("Should throw exception when countryIso2 is null")
    void shouldThrowExceptionWhenCountryIso2IsNull() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> modelService.getAllByCountry(null));

        assertEquals("CountryISO2 must be passed", exception.getMessage());

        verify(headquartersService, never()).getAllByCountry(anyString());
        verify(branchService, never()).getAllByCountry(anyString());
        verify(utilsRepository, never()).findCountryNameByISO2(anyString());
    }
}

