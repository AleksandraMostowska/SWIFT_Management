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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UpdateTest {

    @Mock
    private Jdbi jdbi;

    @Mock
    private Handle handle;

    @InjectMocks
    private BranchRepositoryImpl branchRepository;

    private Branch branch;

    @BeforeEach
    void setUp() {
        branch = Branch.builder()
                .address("Test Address")
                .bankName("Test Bank")
                .countryIso2("PL")
                .countryName("POLAND")
                .isHeadquarter(false)
                .swiftCode("PLNXXX12345")
                .swiftPrefix("PLNXXX12")
                .townName("Test Town")
                .timeZone("CET")
                .codeType("Test CodeType")
                .build();
    }

    @Test
    @DisplayName("Should update entity successfully")
    void shouldUpdateEntitySuccessfully() {
        when(jdbi.withHandle(Mockito.any()))
                .thenReturn(1)
                .thenReturn(Optional.of(branch));
//        when(branchRepository.findBySwiftCode(Mockito.anyString()))
//                .thenReturn(Optional.of(branch));

        var result = branchRepository.update("PLNXXX12345", branch);

        assertNotNull(result);
        var branchDto = branch.toGetBranchDto();
        var resultDto = result.toGetBranchDto();
        assertEquals(branchDto.swiftCode(), resultDto.swiftCode());
        assertEquals("Test Address", resultDto.address());

        verify(jdbi, times(2)).withHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should throw exception when update fails")
    void shouldThrowExceptionWhenUpdateFails() {
        when(jdbi.withHandle(any())).thenReturn(0);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> branchRepository.update("PLNXXX12345", branch));

        assertEquals("Update not completed", exception.getMessage());
    }
}

