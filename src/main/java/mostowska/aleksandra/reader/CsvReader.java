package mostowska.aleksandra.reader;

import mostowska.aleksandra.model.dto.csv.CreateModelFromCsvDto;
import org.apache.commons.csv.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * CsvReader is a utility class responsible for reading and parsing CSV files
 * and converting each row into a list of `CreateModelFromCsvDto` objects.
 */
public class CsvReader {

    /**
     * Reads a CSV file and maps each record to a `CreateModelFromCsvDto` object.
     * The method expects a CSV file to be present in the resources folder.
     *
     * @param fileName The name of the CSV file to be read.
     * @return A list of `CreateModelFromCsvDto` objects populated with the data from the CSV file.
     * @throws IOException If there is an issue with reading the file, such as a file not found or a parsing error.
     */
    public static List<CreateModelFromCsvDto> readCsv(String fileName) throws IOException {
        var data = new ArrayList<CreateModelFromCsvDto>();

        InputStream inputStream = CsvReader.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new FileNotFoundException(fileName + " has not been found in resources.");
        }

        try (var reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             var csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (var csvRecord : csvParser) {
                var countryISO2 = csvRecord.get("COUNTRY ISO2 CODE").trim().toUpperCase();
                var swiftCode = csvRecord.get("SWIFT CODE").trim();
                var codeType = csvRecord.get("CODE TYPE").trim();
                var bankName = csvRecord.get("NAME").trim().toUpperCase();
                var address = csvRecord.get("ADDRESS").trim();
                var townName = csvRecord.get("TOWN NAME").trim();
                var countryName = csvRecord.get("COUNTRY NAME").trim();
                var timeZone = csvRecord.get("TIME ZONE").trim();

                data.add(new CreateModelFromCsvDto(
                        countryISO2,
                        swiftCode,
                        codeType,
                        bankName,
                        address,
                        townName,
                        countryName,
                        timeZone
                ));
            }
        }

        return data;
    }
}
