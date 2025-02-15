package mostowska.aleksandra.repository.model.impl;

import mostowska.aleksandra.model.impl.Headquarters;
import mostowska.aleksandra.repository.generic.AbstractCrudRepository;
import mostowska.aleksandra.repository.model.HeadquartersRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the `HeadquartersRepository` interface.
 * This class provides the actual SQL queries and database interactions
 * for the `headquarters` entity.
 */
@Repository
public class HeadquartersRepositoryImpl extends AbstractCrudRepository<Headquarters> implements HeadquartersRepository {

    /**
     * Constructor that initializes the Jdbi object used for database interactions.
     *
     * @param jdbi The Jdbi object to interact with the database.
     */
    public HeadquartersRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    /**
     * Finds all headquarters entities in the database that have a specific SWIFT code.
     *
     * @param swiftCode The SWIFT code to search for.
     * @return A list of `Headquarters` entities matching the given SWIFT code.
     */
    @Override
    public List<Headquarters> findAllForSWIFT(String swiftCode) {
        var sql = "select * from headquarters where swift_code = :swift_code";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("swift_code", swiftCode)
                .mapToBean(Headquarters.class)
                .collect(Collectors.toList()));
    }

    /**
     * Finds all headquarters entities in the database that are located in a specific country.
     *
     * @param countryISO2 The ISO2 country code to search for.
     * @return A list of `Headquarters` entities located in the specified country.
     */
    @Override
    public List<Headquarters> findAllForCountry(String countryISO2) {
        var sql = "select * from headquarters where country_iso2 = :country_iso2";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("country_iso2", countryISO2)
                .mapToBean(Headquarters.class)
                .collect(Collectors.toList()));
    }
}

