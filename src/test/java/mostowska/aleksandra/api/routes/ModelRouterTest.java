//package mostowska.aleksandra.api.routes;
//
//import com.google.gson.Gson;
//import mostowska.aleksandra.api.dto.ResponseDto;
//import mostowska.aleksandra.model.dto.CreateModelDto;
//import mostowska.aleksandra.model.dto.branch.GetBranchDto;
//import mostowska.aleksandra.service.HeadquartersService;
//import mostowska.aleksandra.service.ModelService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import spark.ResponseTransformer;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import static spark.Spark.*;
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.Unirest;
//
//import java.util.Collections;
//
//@ExtendWith(MockitoExtension.class)
//public class ModelRouterTest {
//
//    @Mock
//    private ModelService modelService;
//
//    @Mock
//    private HeadquartersService headquartersService;
//
//    @Mock
//    private ResponseTransformer responseTransformer;
//
//    @Mock
//    private Gson gson;
//
//    private ModelRouter modelRouter;
//
//    @BeforeEach
//    void setUp() {
//        // Setup ModelRouter instance
//        modelRouter = new ModelRouter(modelService, headquartersService, responseTransformer, gson);
//        // Start Spark for testing
//        port(4567); // Set a custom test port if needed
//        modelRouter.routes();
//    }
//
//    @Test
//    void shouldReturnTestRouteMessage() throws Exception {
//        // Send GET request to the /test route
//        var response = Unirest.get("http://localhost:4567/v1/test").asString();
//
//        // Assert the response
//        assertEquals(200, response.getStatus());
//        assertEquals("Test route is working inside model router!", response.getBody());
//    }
//
//    @Test
//    void shouldReturnHeadquartersList() throws Exception {
//        // Mock data for headquarters
//        when(headquartersService.getAll()).thenReturn(Collections.emptyList());
//
//        // Send GET request to /swift-codes/headquarters
//        var response = Unirest.get("http://localhost:4567/v1/swift-codes/headquarters")
//                .header("Accept", "application/json")
//                .asJson();
//
//        // Assert the response
//        assertEquals(200, response.getStatus());
//        assertNotNull(response.getBody());
//        verify(headquartersService, times(1)).getAll();
//    }
//
//    @Test
//    void shouldCreateNewModel() throws Exception {
//        // Mock data for creating a model
//        var createModelDto = new CreateModelDto(
//                "Some address",
//                "Some Bank",
//                "PL",
//                "Poland",
//                true,
//                "PLNXXX12345"
//        );
//
//        var getBranchDto = new GetBranchDto("Test Address", "Test Bank", "PL",
//                "POLAND", false, "PLNXXX12345");
//
//        when(gson.fromJson(anyString(), eq(CreateModelDto.class))).thenReturn(createModelDto);
//        when(modelService.saveModelToDB(any(CreateModelDto.class))).thenReturn(getBranchDto);
//
//        // Send POST request to /swift-codes
//        HttpResponse<JsonNode> response = Unirest.post("http://localhost:4567/v1/swift-codes")
//                .header("Content-Type", "application/json")
//                .body(gson.toJson(createModelDto))
//                .asJson();
//
//        // Assert the response
//        assertEquals(201, response.getStatus());
//        assertTrue(response.getBody().toString().contains("PLNXXX12345"));
//        verify(modelService, times(1)).saveModelToDB(createModelDto);
//    }
//
//    @Test
//    void shouldDeleteModelBySwiftCode() throws Exception {
//        var swiftCode = "PLNXXX12345";
//        var getBranchDto = new GetBranchDto("Test Address", "Test Bank", "PL",
//                "POLAND", false, "PLNXXX12345");
//        when(modelService.delete(swiftCode)).thenReturn(getBranchDto);
//
//        // Send DELETE request to /swift-codes/:swift-code
//        HttpResponse<JsonNode> response = Unirest.delete("http://localhost:4567/v1/swift-codes/" + swiftCode)
//                .header("Accept", "application/json")
//                .asJson();
//
//        // Assert the response
//        assertEquals(200, response.getStatus());
//        assertTrue(response.getBody().toString().contains("Deleted"));
//        verify(modelService, times(1)).delete(swiftCode);
//    }
//
//    @Test
//    void shouldReturnErrorForInvalidRoute() throws Exception {
//        // Send GET request to an invalid route
//        var response = Unirest.get("http://localhost:4567/invalid/route").asString();
//
//        // Assert the response
//        assertEquals(404, response.getStatus());
//        assertTrue(response.getBody().contains("Not found"));
//    }
//
//    @Test
//    void shouldHandleRuntimeException() throws Exception {
//        // Simulate a runtime exception in the service
//        when(modelService.getAllHeadquartersWithBranches(anyString())).thenThrow(new RuntimeException("Test error"));
//
//        // Send GET request to /swift-codes/:swift-code
//        var response = Unirest.get("http://localhost:4567/v1/swift-codes/PLNXXX12345").asString();
//
//        // Assert the response
//        assertEquals(500, response.getStatus());
//        assertTrue(response.getBody().contains("Internal Server Error"));
//    }
//
//    @AfterEach
//    void tearDown() {
//        stop(); // Stop the Spark server after tests
//    }
//}
//
//
