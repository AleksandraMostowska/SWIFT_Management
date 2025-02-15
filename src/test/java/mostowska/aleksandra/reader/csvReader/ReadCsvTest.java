//package mostowska.aleksandra.reader.csvReader;
//
//import mostowska.aleksandra.model.dto.csv.CreateModelFromCsvDto;
//import mostowska.aleksandra.reader.CsvReader;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.*;
//import java.util.Collections;
//import java.util.List;
//import java.nio.charset.StandardCharsets;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//
//
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//
//@ExtendWith(MockitoExtension.class)
//class ReadCsvTest {
//
//    @InjectMocks
//    private CsvReader csvReader;  // We want to test the CsvReader class
//
//    @Mock
//    private InputStreamReader inputStreamReader;  // Mocking the InputStreamReader
//
//    @Test
//    void shouldReadAndMapCsvDataCorrectly() throws IOException {
//        String csvData = "COUNTRY ISO2 CODE,SWIFT CODE,CODE TYPE,NAME,ADDRESS,TOWN NAME,COUNTRY NAME,TIME ZONE\n" +
//                "PL,PLNXXX12345,Test Code Type,Test Bank,Test Address,Test Town,POLAND,CET";
//
//        // Convert the string to an InputStream
//        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));
//
//        // Simulate that getResourceAsStream() returns the mocked InputStream
//        Mockito.when(csvReader.getClass().getClassLoader().getResourceAsStream("test.csv")).thenReturn(inputStream);
//
//        // Test the method that calls readCsv
//        List<CreateModelFromCsvDto> result = CsvReader.readCsv("test.csv");
//
//        // Assertions to verify that the data is mapped correctly
//        assertNotNull(result);
//        assertEquals(1, result.size()); // One record in the CSV
//
//        var firstItem = result.get(0);
//        assertEquals("PL", firstItem.countryISO2());
//        assertEquals("PLNXXX12345", firstItem.swiftCode());
//        assertEquals("Test Code Type", firstItem.codeType());
//        assertEquals("Test Bank", firstItem.bankName());
//        assertEquals("Test Address", firstItem.address());
//        assertEquals("Test Town", firstItem.townName());
//        assertEquals("POLAND", firstItem.countryISO2());
//        assertEquals("CET", firstItem.timeZone());
//    }
//
//    @Test
//    void shouldReturnEmptyListForEmptyCsvFile() throws IOException {
//        String csvData = "";
//
//        // Convert the string to an InputStream
//        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));
//
//        // Simulate that getResourceAsStream() returns the mocked InputStream
//        Mockito.when(csvReader.getClass().getClassLoader().getResourceAsStream("empty.csv")).thenReturn(inputStream);
//
//        // Test the method that calls readCsv
//        List<CreateModelFromCsvDto> result = CsvReader.readCsv("empty.csv");
//
//        // Assertions to verify that the result is empty
//        assertNotNull(result);
//        assertTrue(result.isEmpty()); // Should return an empty list
//    }
//}
//
//
