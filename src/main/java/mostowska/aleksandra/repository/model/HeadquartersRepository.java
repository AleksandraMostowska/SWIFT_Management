package mostowska.aleksandra.repository.model;

import mostowska.aleksandra.model.impl.Headquarters;
import mostowska.aleksandra.repository.generic.CrudRepository;

import java.util.List;

/**
 * Repository interface for interacting with the `headquarters` table in the database.
 * This interface extends the generic `CrudRepository` to provide additional
 * query methods specific to the `headquarters` entity.
 */
public interface HeadquartersRepository extends CrudRepository<Headquarters> {

    /**
     * Finds all headquarters entities with a specific SWIFT code.
     *
     * @param swiftCode The SWIFT code to search for.
     * @return A list of `Headquarters` entities matching the given SWIFT code.
     */
    List<Headquarters> findAllForSWIFT(String swiftCode);

    /**
     * Finds all headquarters entities located in a specific country.
     *
     * @param countryISO2 The ISO2 code of the country to search for.
     * @return A list of `Headquarters` entities located in the specified country.
     */
    List<Headquarters> findAllForCountry(String countryISO2);
}

