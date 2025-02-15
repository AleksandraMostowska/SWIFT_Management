package mostowska.aleksandra.repository.model.impl;

import mostowska.aleksandra.model.impl.Branch;
import mostowska.aleksandra.repository.generic.AbstractCrudRepository;
import mostowska.aleksandra.repository.model.BranchRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of the `BranchRepository` interface.
 * This class provides the actual SQL queries and database interactions
 * for the `branches` entity.
 */

@Repository
public class BranchRepositoryImpl extends AbstractCrudRepository<Branch> implements BranchRepository {

    /**
     * Constructor that initializes the Jdbi object used for database interactions.
     *
     * @param jdbi The Jdbi object to interact with the database.
     */
    public BranchRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    /**
     * Finds all branches entities in the database that have a specific SWIFT code.
     *
     * @param swiftCode The SWIFT code to search for.
     * @return A list of `Branches` entities matching the given SWIFT code.
     */
    @Override
    public List<Branch> findAllForSWIFT(String swiftCode) {
        String swiftPrefix = swiftCode.substring(0, 8);
        var sql = "select * from branches where swift_prefix = :swift_prefix";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("swift_prefix", swiftPrefix)
                .mapToBean(Branch.class)
                .collect(Collectors.toList()));
    }

    /**
     * Finds all branches entities in the database that are located in a specific country.
     *
     * @param countryISO2 The ISO2 country code to search for.
     * @return A list of `Branches` entities located in the specified country.
     */
    @Override
    public List<Branch> findAllForCountry(String countryISO2) {
        var sql = "select * from branches where country_iso2 = :country_iso2";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("country_iso2", countryISO2)
                .mapToBean(Branch.class)
                .collect(Collectors.toList()));
    }
}
