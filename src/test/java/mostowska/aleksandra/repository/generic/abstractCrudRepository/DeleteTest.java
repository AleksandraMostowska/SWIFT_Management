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
class DeleteTest {

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
    @DisplayName("Should delete entity successfully")
    void shouldDeleteEntitySuccessfully() {
        when(jdbi.withHandle(Mockito.any())).thenReturn(Optional.of(branch));

        doNothing().when(jdbi).useHandle(Mockito.any());

        var result = branchRepository.delete("PLNXXX12345");

        assertNotNull(result);
        assertEquals("PLNXXX12345", result.toGetBranchDto().swiftCode());

        verify(jdbi, times(1)).useHandle(Mockito.any());
    }

    @Test
    @DisplayName("Should throw exception when no item to delete")
    void shouldThrowExceptionWhenNoItemToDelete() {
        when(jdbi.withHandle(Mockito.any())).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> branchRepository.delete("PLNXXX12345"));

        assertEquals("No item to delete", exception.getMessage());

        verify(jdbi, times(0)).useHandle(Mockito.any());
    }
}

