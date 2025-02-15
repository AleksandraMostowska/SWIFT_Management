package mostowska.aleksandra.repository.generic;

import java.util.List;
import java.util.Optional;

/**
 * CrudRepository is a generic interface that defines the basic CRUD operations.
 * This interface can be implemented for various entities.
 *
 * @param <T> The type of the entity.
 */
public interface CrudRepository<T> {
    /**
     * Saves a new entity in the repository.
     *
     * @param item The entity to be saved.
     * @return The saved entity.
     */
    T save(T item);


    /**
     * Updates an existing entity in the repository.
     *
     * @param swiftCode The `swiftCode` of the entity to update.
     * @param item The entity with updated data.
     * @return The updated entity.
     */
    T update(String swiftCode, T item);

    /**
     * Finds an entity by its `swiftCode`.
     *
     * @param swiftCode The `swiftCode` of the entity.
     * @return An Optional containing the entity if found, otherwise empty.
     */
    Optional<T> findBySwiftCode(String swiftCode);

    /**
     * Saves multiple entities in the repository.
     *
     * @param items The list of entities to be saved.
     * @return The list of saved entities.
     */
    List<T> saveAll(List<T> items);


    /**
     * Finds the last 'n' entities in the repository.
     *
     * @param n The number of entities to retrieve.
     * @return A list of the last 'n' entities.
     */
    List<T> findLast(int n);

    /**
     * Retrieves all entities in the repository.
     *
     * @return A list of all entities.
     */
    List<T> findAll();


    /**
     * Deletes an entity by its `swiftCode`.
     *
     * @param swiftCode The `swiftCode` of the entity to delete.
     * @return The deleted entity.
     */
    T delete(String swiftCode);

    /**
     * Finds entities by a list of `swiftCode`s.
     *
     * @param swiftCodes The list of `swiftCode`s.
     * @return A list of entities corresponding to the given `swiftCode`s.
     */
    List<T> findAllBySwiftCode(List<String> swiftCodes);


    /**
     * Deletes multiple entities by a list of `swiftCode`s.
     *
     * @param swiftCodes The list of `swiftCode`s.
     * @return A list of the deleted entities.
     */
    List<T> deleteAllBySwiftCode(List<String> swiftCodes);

    /**
     * Deletes all entities in the repository.
     *
     * @return A list of the deleted entities.
     */
    List<T> deleteAll();
}
