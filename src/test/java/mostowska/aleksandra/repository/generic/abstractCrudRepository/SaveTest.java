package mostowska.aleksandra.repository.generic.abstractCrudRepository;

import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.repository.model.BranchRepository;
import mostowska.aleksandra.repository.model.impl.BranchRepositoryImpl;
import org.jdbi.v3.core.HandleCallback;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.Handle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class SaveTest {

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
    @DisplayName("Should save entity successfully")
    void shouldSaveEntitySuccessfully() {
//        when(branchRepository.findBySwiftCode(Mockito.any())).thenReturn(Optional.of(branch));
//        when(jdbi.withHandle(Mockito.any())).thenReturn(1);
//        when(branchRepository.findBySwiftCode(Mockito.any())).thenCallRealMethod()
//                .thenReturn(Optional.of(branch));

//        when(jdbi.withHandle(any()))
//                .thenReturn(1)
//                .thenAnswer(invocation -> {
////            HandleCallback<Optional<Branch>, Exception> callback = invocation.getArgument(0);
//            return Optional.of(branch);
//        });

        when(jdbi.withHandle(any()))
                .thenReturn(1)
                .thenReturn(Optional.of(branch));

        var result = branchRepository.save(branch);
        assertNotNull(result);
        var resultDto = result.toGetBranchDto();

         assertEquals("Test Address", resultDto.address());
         assertEquals("Test Bank", resultDto.bankName());
         assertEquals("POLAND", resultDto.countryName());
         assertEquals("PLNXXX12345", resultDto.swiftCode());

        verify(jdbi, times(2)).withHandle(any());
    }

    @Test
    @DisplayName("Should throw exception when insertion fails")
    void shouldThrowExceptionWhenInsertionFails() {
        when(jdbi.withHandle(any())).thenReturn(0);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> branchRepository.save(branch));

        assertEquals("Row not inserted", exception.getMessage());
    }
}

