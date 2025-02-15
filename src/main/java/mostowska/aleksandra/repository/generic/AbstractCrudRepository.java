package mostowska.aleksandra.repository.generic;

import com.google.common.base.CaseFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atteo.evo.inflector.English;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.joining;

/**
 * AbstractCrudRepository is an abstract implementation of the CrudRepository interface.
 * It provides the basic functionality for CRUD operations using Jdbi.
 *
 * @param <T> The type of the entity.
 */
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractCrudRepository<T> implements CrudRepository<T> {
    protected final Jdbi jdbi;

    // This retrieves the actual entity type from the generic type of the subclass
    @SuppressWarnings("unchecked")
    private final Class<T> entityType
            = (Class<T>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    /**
     * Saves a new entity in the repository.
     * This method inserts the entity into the database.
     *
     * @param item The entity to be saved.
     * @return The saved entity.
     */
    @Override
    public T save(T item) {
        var sql = "insert into %s %s values %s;".formatted(
                tableName(),
                columnNamesForInsert(),
                columnValuesForInsert(item)
        );
        var insertedRows = jdbi.withHandle(handle -> handle.execute(sql));

        if (insertedRows == 0) {
            throw new IllegalStateException("Row not inserted");
        }
        return findBySwiftCode(getSwiftCode(item)).orElseThrow();
    }

    /**
     * Updates an existing entity in the repository.
     * This method updates the entity with the provided swiftCode in the database.
     *
     * @param swiftCode The swiftCode of the entity to update.
     * @param item The entity with updated data.
     * @return The updated entity.
     */
    @Override
    public T update(String swiftCode, T item) {
        var sql = "update %s set %s where swift_code = :swift_code".formatted(
                tableName(),
                columnNamesAndValuesForUpdate(item)
        );
        var updatedRows = jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("swift_code", swiftCode)
                .execute());

        if (updatedRows == 0) {
            throw new IllegalStateException("Update not completed");
        }

        return findBySwiftCode(swiftCode).orElseThrow();
    }

    /**
     * Retrieves an entity from the database based on its `swiftCode`.
     *
     * @param swiftCode The `swiftCode` of the entity.
     * @return An Optional containing the entity if found, otherwise empty.
     */
    @Override
    public Optional<T> findBySwiftCode(String swiftCode) {
        var sql = "select * from " + tableName() + " where swift_code = :swift_code";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("swift_code", swiftCode)
                .mapToBean(entityType)
                .findFirst()
        );
    }


    /**
     * Saves multiple entities in the database.
     *
     * @param items The list of entities to save.
     * @return The list of saved entities.
     */
    @Override
    public List<T> saveAll(List<T> items) {
        var sql = "insert into %s %s values %s".formatted(
                tableName(),
                columnNamesForInsert(),
                items
                        .stream()
                        .map(this::columnValuesForInsert)
                        .collect(joining(", "))
        );
        var insertedRows = jdbi.withHandle(handle -> handle.execute(sql));
        if (insertedRows == 0) {
            throw new IllegalStateException("Rows not inserted");
        }
        return findLast(insertedRows);
    }

    /**
     * Finds the last 'n' entities from the database.
     *
     * @param n The number of entities to retrieve.
     * @return A list of the last 'n' entities.
     */
    @Override
    public List<T> findLast(int n) {
        var sql = "select * from " + tableName() + " order by swift_code desc limit :n";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("n", n)
                .mapToBean(entityType)
                .list()
        );
    }

    /**
     * Retrieves all entities from the database.
     *
     * @return A list of all entities.
     */
    @Override
    public List<T> findAll() {
        var sql = "select * from " + tableName();
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .mapToBean(entityType)
                .list()
        );
    }


    /**
     * Deletes a single entity from the database based on its `swiftCode`.
     * This method deletes the entity with the given `swiftCode` from the database.
     *
     * @param swiftCode The swiftCode of the entity to delete.
     * @return The entity that was deleted.
     * @throws IllegalStateException if the entity with the provided `swiftCode` is not found.
     */
    @Override
    public T delete(String swiftCode) {
        var itemToDelete = findBySwiftCode(swiftCode)
                .orElseThrow(() -> new IllegalStateException("No item to delete"));

        var sql = "delete from " + tableName() + " where swift_code = :swift_code";
        jdbi.useHandle(handle -> handle
                .createUpdate(sql)
                .bind("swift_code", swiftCode)
                .execute());
        return itemToDelete;
    }


    /**
     * Finds multiple entities based on a list of `swiftCode`s.
     * This method retrieves entities whose `swiftCode` matches any of the swiftCodes in the provided list.
     *
     * @param swiftCodes A list of swiftCodes to look for in the database.
     * @return A list of entities corresponding to the provided swiftCodes.
     * @throws IllegalStateException if not all the provided swiftCodes are found in the table.
     */
    @Override
    public List<T> findAllBySwiftCode(List<String> swiftCodes) {
        var sql = "select * from " + tableName() + " where swift_code in (<swiftCodes>)";
        var items = jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bindList("swiftCodes", swiftCodes)
                .mapToBean(entityType)
                .list());

        if (items.isEmpty()) {
            return List.of();
        }

        if (items.size() != swiftCodes.size()) {
            throw new IllegalStateException("Not all swiftCodes are present in table");
        }

        return items;
    }


    /**
     * Deletes multiple entities from the database based on the provided list of `swiftCode`s.
     * This method deletes all entities whose `swiftCode` is contained in the provided list.
     *
     * @param swiftCodes A list of swiftCodes identifying the entities to be deleted.
     * @return A list of the entities that were deleted.
     * @throws IllegalStateException if not all the swiftCodes are found in the table.
     */
    @Override
    public List<T> deleteAllBySwiftCode(List<String> swiftCodes) {
        var items = findAllBySwiftCode(swiftCodes);
        var sql = "delete from " + tableName() + " where swift_code in (<swiftCodes>)";
        jdbi.useHandle(handle -> handle
                .createUpdate(sql)
                .bindList("swiftCodes", swiftCodes)
                .execute());
        return items;
    }

    /**
     * Deletes all entities in the database.
     *
     * @return A list of the deleted entities.
     */
    @Override
    public List<T> deleteAll() {
        var items = findAll();
        var sql = "delete from " + tableName();
        jdbi.useHandle(handle -> handle.execute(sql));
        return items;  // Returns the list of deleted entities
    }

    /**
     * Converts a string from UpperCamelCase to lower_underscore format.
     *
     * @param upperCamel The string to convert.
     * @return The converted string in lower_underscore format.
     */
    private String toLowerUnderscore(String upperCamel) {
        return CaseFormat.UPPER_CAMEL.to(
                CaseFormat.LOWER_UNDERSCORE,
                upperCamel);
    }

    /**
     * Retrieves the table name for the entity based on its type.
     *
     * @return The table name as a string.
     */
    private String tableName() {
        return English.plural(toLowerUnderscore(entityType.getSimpleName()));
    }

    /**
     * Retrieves the column names for a select statement.
     *
     * @return An array of column names.
     */
    private String[] columnNamesForSelect() {
        return Arrays
                .stream(entityType.getDeclaredFields())
                .map(field -> toLowerUnderscore(field.getName()))
                .toArray(String[]::new);
    }

    /**
     * Retrieves the column names for an insert statement.
     * This helper method retrieves the columns that need to be inserted into the database.
     *
     * @return A formatted string of column names.
     */
    private String columnNamesForInsert() {
        var cols = Arrays
                .stream(getAllFields(entityType))
                .filter(field -> !field.getName().equalsIgnoreCase("swift_code"))
                .map(field -> toLowerUnderscore(field.getName()))
                .collect(joining(", "));
        return "( %s )".formatted(cols);
    }



    /**
     * Generates the SQL values for an `INSERT` statement based on the entity's fields.
     * This method prepares the field values of the entity for inclusion in an SQL query.
     * It handles fields of type `String`, `Enum`, `LocalDateTime`, `LocalDate`, and others, converting them to the appropriate SQL format.
     *
     * @param item The entity whose values will be inserted.
     * @return A formatted string representing the values for the `INSERT` statement.
     * @throws IllegalStateException if an error occurs while accessing the fields or their values.
     */
    private String columnValuesForInsert(T item) {
        var values = Arrays
                .stream(getAllFields(entityType))
                .filter(field -> !field.getName().equalsIgnoreCase("id"))
                .map(field -> {
                    try {
                        field.setAccessible(true);

                        if (field.get(item) == null) {
                            return "NULL";
                        }

                        if (List.of(
                                String.class,
                                Enum.class,
                                LocalDateTime.class,
                                LocalDate.class).contains(field.getType())) {
                            String fieldValue = (String) field.get(item);
                            return "'%s'".formatted(escapeSingleQuotes(fieldValue));
                        }

                        return field.get(item).toString();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }).collect(joining(", "));
        return "( %s )".formatted(values);
    }



    /**
     * Retrieves the column names and values for an update statement.
     *
     * @param item The entity to extract column names and values from.
     * @return A formatted string of column names and values for update.
     */
    private String columnNamesAndValuesForUpdate(T item) {
        return Arrays
                .stream(entityType.getDeclaredFields())
                .filter(field -> {
                    try {
                        field.setAccessible(true);
                        return !field.getName().equalsIgnoreCase("id") && field.get(item) != null;
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .map(field -> {
                    try {
                        field.setAccessible(true);

                        if (List.of(
                                String.class,
                                Enum.class,
                                LocalDateTime.class,
                                LocalDate.class).contains(field.getType())) {
                            return "%s='%s'".formatted(
                                    toLowerUnderscore(field.getName()),
                                    field.get(item));
                        }
                        return toLowerUnderscore(field.getName()) + "=" + field.get(item).toString();  // Returns field name and value
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }).collect(joining(", "));
    }

    /**
     * Escapes single quotes in a string to ensure proper formatting in SQL queries.
     * This method converts any single quote in the string to two single quotes ('').
     *
     * @param value The string value to escape.
     * @return The escaped string.
     */
    private String escapeSingleQuotes(String value) {
        if (value == null) {
            return "NULL";
        }
        return value.replace("'", "''");
    }


    /**
     * Retrieves the `swiftCode` from the given entity.
     * This method reflects over the fields of the entity and extracts the value of the `swiftCode` field.
     *
     * @param item The entity from which to retrieve the swiftCode.
     * @return The `swiftCode` value of the entity.
     * @throws IllegalStateException if no field named `swiftCode` is found or an error occurs.
     */
    private String getSwiftCode(T item) {
        try {
            Field swiftCodeField = Arrays.stream(getAllFields(entityType))
                    .filter(field -> field
                            .getName()
                            .equalsIgnoreCase("swiftCode"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No swiftCode field found"));

            swiftCodeField.setAccessible(true);
            return (String) swiftCodeField.get(item);
        } catch (Exception e) {
            throw new IllegalStateException("Error retrieving swift_code", e);
        }
    }

    /**
     * Retrieves all fields of the entity class and its superclasses.
     * This method gets all declared fields (including inherited ones) of the entity class.
     *
     * @param clazz The class to inspect.
     * @return An array of Field objects representing the entity's fields.
     */
    private Field[] getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }
}
