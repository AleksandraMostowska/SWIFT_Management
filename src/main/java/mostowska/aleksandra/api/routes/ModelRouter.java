package mostowska.aleksandra.api.routes;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.api.dto.ResponseDto;
import mostowska.aleksandra.model.dto.CreateModelDto;
import mostowska.aleksandra.service.HeadquartersService;
import mostowska.aleksandra.service.ModelService;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import static spark.Spark.*;

/**
 * The `ModelRouter` class is responsible for defining the API routes and their corresponding handlers.
 * It uses Spark framework to handle HTTP requests and responses. The routes are related to managing and retrieving
 * information about swift codes and model data for headquarters and branches.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ModelRouter {
    private final ModelService modelService;
    private final HeadquartersService headquartersService;
    private final ResponseTransformer responseTransformer;
    private final Gson gson;

    /**
     * Define the routes for the API.
     * These routes handle different HTTP methods such as GET, POST, and DELETE.
     */
    public void routes() {

        path("/v1", () -> {

            // Route for testing the API.
            get("/test", (req, res) -> {
                log.info("GET /test called");
                return "Test route is working inside model router!";
            });

            // Route for retrieving all headquarters (GET request).
            get(
                    "/swift-codes/headquarters",
                    (request, response) -> {
                        Utils.setResponse(response, 200);
//                        return new ResponseDto<>(headquartersService.getAll());
                        return headquartersService.getAll();
                    },
                    responseTransformer
            );

            // Route for retrieving headquarters by a specific swift code (GET request).
            get(
                    "/swift-codes/headquarters/:swift-code",
                    (request, response) -> {
                        var swiftCode = String.valueOf(request.params(":swift-code"));
                        Utils.setResponse(response, 200);
                        return new ResponseDto<>(headquartersService.getAllBySwiftCode(swiftCode));
                    },
                    responseTransformer
            );

            // Route for retrieving all headquarters and branches by swift code (GET request).
            get(
                    "/swift-codes/:swift-code",
                    (request, response) -> {
                        var swiftCode = String.valueOf(request.params(":swift-code"));
                        Utils.setResponse(response, 200);
                        return new ResponseDto<>(modelService.getAllHeadquartersWithBranches(swiftCode));
                    },
                    responseTransformer
            );

            // Route for retrieving all models by country ISO2 code (GET request).
            get(
                    "/swift-codes/country/:countryISO2code",
                    (request, response) -> {
                        var countryIso2 = String.valueOf(request.params(":countryISO2code"));
                        Utils.setResponse(response, 200);
                        return new ResponseDto<>(modelService.getAllByCountry(countryIso2));
                    },
                    responseTransformer
            );

            // Route for creating a new model (POST request).
            post(
                    "/swift-codes",
                    (request, response) -> {
                        var createModelDto = gson.fromJson(request.body(), CreateModelDto.class);
                        Utils.setResponse(response, 201);
                        return new ResponseDto<>(modelService.saveModelToDB(createModelDto));
                    },
                    responseTransformer
            );

            // Route for deleting a model by swift code (DELETE request).
            delete(
                    "/swift-codes/:swift-code",
                    (request, response) -> {
                        var swiftCode = String.valueOf(request.params(":swift-code"));
                        Utils.setResponse(response, 200);
                        return new ResponseDto<>(modelService.delete(swiftCode));
                    },
                    responseTransformer
            );
        });

        // Global exception handler for runtime exceptions.
        exception(RuntimeException.class, (ex, request, response) -> {
            var exceptionMessage = ex.getMessage();
            System.out.println("EX: " + exceptionMessage);
            response.redirect("/error?msg=" + exceptionMessage, 301);
        });

        // Error route to display error messages.
        path("/error", () ->
                get(
                        "",
                        (request, response) -> {
                            Utils.setResponse(response, 500);
                            var message = request.queryParams("msg");
                            return new ResponseDto<>(message);
                        },
                        responseTransformer
                )
        );

        // Global handler for internal server errors.
        internalServerError((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            return gson.toJson(new ResponseDto<>("Internal Server Error"));
        });

        // Global handler for not found errors (404).
        notFound((request, response) -> {
            response.header("Content-Type", "application/json;charset=utf-8");
            return gson.toJson(new ResponseDto<>("Not found"));
        });
    }
}
