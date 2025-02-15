package mostowska.aleksandra;

import mostowska.aleksandra.api.routes.ModelRouter;
import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static spark.Spark.*;

@Slf4j
public class App {
    public static void main(String[] args) {
        log.info("Setting up routes");

        initExceptionHandler(err -> System.out.println(err.getMessage()));
        port(8080);

//      var data = CsvReader.readCsv("SWIFT.csv");
//            var createHeadquartersTableSql = """
//                        CREATE TABLE IF NOT EXISTS headquarters (
//                                  swift_code VARCHAR(20) PRIMARY KEY,
//                                  swift_prefix VARCHAR(8) NOT NULL UNIQUE,
//                                  country_iso2 VARCHAR(2) NOT NULL,
//                                  code_type VARCHAR(50),
//                                  bank_name VARCHAR(100),
//                                  address VARCHAR(255),
//                                  town_name VARCHAR(100),
//                                  country_name VARCHAR(100),
//                                  time_zone VARCHAR(50),
//                                  is_headquarter BOOLEAN DEFAULT TRUE
//                              );
//                    """;
//
//            var createBranchesTableSql = """
//                        CREATE TABLE IF NOT EXISTS branches (
//                            swift_code VARCHAR(20) PRIMARY KEY,
//                            swift_prefix VARCHAR(8),
//                            country_iso2 VARCHAR(2) NOT NULL,
//                            code_type VARCHAR(50),
//                            bank_name VARCHAR(100),
//                            address VARCHAR(255),
//                            town_name VARCHAR(100),
//                            country_name VARCHAR(100),
//                            time_zone VARCHAR(50),
//                            is_headquarter BOOLEAN DEFAULT FALSE
//                        );
//                    """;
//        var jdbi = context.getBean(Jdbi.class);
//        jdbi.useHandle(handle -> {
//            handle.execute(createHeadquartersTableSql);  // Execute HQ table creation
//            handle.execute(createBranchesTableSql);      // Execute Branches table creation
//        });

        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        var modelRouter = context.getBean("modelRouter", ModelRouter.class);
        modelRouter.routes();
    }

//    public static void saveToDBFromCsv(List<CreateModelDto> data, ModelService modelService) {
//        for (var d : data) {
//            try {
//                modelService.saveModelToDB(d);
//            } catch (Exception e) {
//                log.error("Failed to save model: " + d.swiftCode(), e);
//            }
//        }
//    }
}


