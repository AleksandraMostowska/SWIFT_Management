package mostowska.aleksandra.repository.model;

import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.repository.generic.CrudRepository;

import java.util.List;

/**
 * Repository interface for interacting with the `branches` table in the database.
 * This interface extends the generic `CrudRepository` to provide additional
 * query methods specific to the `branches` entity.
 */
public interface BranchRepository extends CrudRepository<Branch> {

    /**
     * Finds all branches entities with a specific SWIFT code.
     *
     * @param swiftCode The SWIFT code to search for.
     * @return A list of `Branches` entities matching the given SWIFT code.
     */
    List<Branch> findAllForSWIFT(String swiftCode);

    /**
     * Finds all branches entities located in a specific country.
     *
     * @param countryISO2 The ISO2 code of the country to search for.
     * @return A list of `Branches` entities located in the specified country.
     */
    List<Branch> findAllForCountry(String countryISO2);
}
