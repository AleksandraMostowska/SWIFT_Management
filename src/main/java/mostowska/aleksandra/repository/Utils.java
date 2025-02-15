package mostowska.aleksandra.repository;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Utility class for interacting with the database to fetch country names by their ISO2 code.
 * This class is used to query both the `headquarters` and `branches` tables
 * to retrieve the corresponding country name based on the ISO2 code.
 */
@Repository
public class Utils {
    private final Jdbi jdbi;

    /**
     * Constructor that initializes the `jdbi` object used for database interactions.
     *
     * @param jdbi The Jdbi object used for querying the database.
     */
    public Utils(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    /**
     * Retrieves the country name based on the provided ISO2 country code.
     * The method first queries the `headquarters` table for the country name.
     * If no result is found, it queries the `branches` table.
     *
     * @param countryIso2 The ISO2 country code to search for.
     * @return The country name corresponding to the given ISO2 code.
     * @throws IllegalStateException if no country name is found in either table.
     */
    public String findCountryNameByISO2(String countryIso2) {
        var sqlHeadquarters = "select country_name from headquarters " +
                              "where country_iso2 = :country_iso2 limit 1";
        Optional<String> countryName = jdbi.withHandle(handle -> handle
                .createQuery(sqlHeadquarters)
                .bind("country_iso2", countryIso2)
                .mapTo(String.class)
                .findFirst());

        if (countryName.isEmpty()) {
            var sqlBranches = "select country_name from branches " +
                              "where country_iso2 = :country_iso2 limit 1";
            countryName = jdbi.withHandle(handle -> handle
                    .createQuery(sqlBranches)
                    .bind("country_iso2", countryIso2)
                    .mapTo(String.class)
                    .findFirst());
        }

        return countryName
                .orElseThrow(() -> new IllegalStateException("No such country"));
    }
}
