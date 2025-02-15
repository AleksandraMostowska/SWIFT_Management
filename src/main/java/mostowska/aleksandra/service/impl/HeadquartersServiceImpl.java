package mostowska.aleksandra.service.impl;

import lombok.RequiredArgsConstructor;
import mostowska.aleksandra.model.dto.CreateModelDto;
import mostowska.aleksandra.model.dto.country.GetModelForCountryDto;
import mostowska.aleksandra.model.dto.headquarters.GetHeadquartersDto;
import mostowska.aleksandra.model.impl.Headquarters;
import mostowska.aleksandra.repository.model.HeadquartersRepository;
import mostowska.aleksandra.service.HeadquartersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the HeadquartersService interface that provides core business logic for managing Headquarters records.
 * It interacts with the HeadquartersRepository to handle database operations and transformation of data.
 */
@Service
@RequiredArgsConstructor
public class HeadquartersServiceImpl implements HeadquartersService {
    private final HeadquartersRepository headquartersRepository;

    /**
     * Adds a new Headquarters based on the provided CreateModelDto and SWIFT code.
     * Validates the DTO, converts it into a Headquarters entity, and saves it to the repository.
     *
     * @param createModelDto DTO containing the information needed to create the Headquarters.
     * @param swiftCode The SWIFT code associated with the Headquarters.
     * @return A DTO representing the added Headquarters.
     */
    @Override
    public GetHeadquartersDto addHeadquarters(CreateModelDto createModelDto, String swiftCode) {
        if (createModelDto == null) {
            throw new IllegalStateException("Item cannot be null");
        }

        createModelDto.verify();

        var headquartersToAdd = createModelDto.toHeadquarters();
        return headquartersRepository.save(headquartersToAdd).toGetHeadquartersDto();
    }

    /**
     * Removes a Headquarters based on the provided SWIFT code.
     * If the Headquarters is not found, an exception is thrown. Otherwise, it is deleted.
     *
     * @param swiftCode The SWIFT code associated with the Headquarters to be removed.
     * @return A DTO representing the deleted Headquarters.
     */
    @Override
    public GetHeadquartersDto removeHeadquarters(String swiftCode) {
        if (swiftCode == null) {
            throw new IllegalStateException("Removal failed");
        }
        if (headquartersRepository.findAllForSWIFT(swiftCode).isEmpty()) {
            throw new IllegalStateException("Item not found");
        }

        return headquartersRepository.delete(swiftCode).toGetHeadquartersDto();
    }

    /**
     * Retrieves all Headquarters records associated with the provided SWIFT code.
     * If no Headquarters are found, an empty list is returned.
     *
     * @param swiftCode The SWIFT code used to filter the Headquarters records.
     * @return A list of DTOs representing the Headquarters associated with the SWIFT code.
     */
    @Override
    public List<GetHeadquartersDto> getAllBySwiftCode(String swiftCode) {
        var headquartersFound = headquartersRepository.findAllForSWIFT(swiftCode);
        return headquartersFound
                .stream()
                .map(Headquarters::toGetHeadquartersDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all Headquarters records.
     *
     * @return A list of DTOs representing all the Headquarters in the repository.
     */
    @Override
    public List<GetHeadquartersDto> getAll() {
        return headquartersRepository
                .findAll()
                .stream()
                .map(Headquarters::toGetHeadquartersDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all Headquarters records associated with the provided country ISO2 code.
     * If no Headquarters are found for the given country, an empty list is returned.
     *
     * @param countryIso2 The 2-letter ISO country code used to filter Headquarters records.
     * @return A list of DTOs representing the Headquarters associated with the country.
     */
    @Override
    public List<GetModelForCountryDto> getAllByCountry(String countryIso2) {
        if (countryIso2 == null) {
            throw new IllegalStateException("Country name must be passed");
        }
        return headquartersRepository.findAllForCountry(countryIso2)
                .stream()
                .map(Headquarters::toGetModelByCountryDto)
                .collect(Collectors.toList());
    }
}
