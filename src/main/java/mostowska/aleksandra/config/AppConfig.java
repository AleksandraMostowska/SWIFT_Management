package mostowska.aleksandra.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


/**
 * Configuration class for setting up application-level beans and settings.
 * This class initializes various components such as Gson, PasswordEncoder,
 * Jdbi for database access, Mailer for email sending, and a SecretKey for JWT.
 */
@Configuration
@ComponentScan("mostowska.aleksandra")
@PropertySource("classpath:/application.properties")
@RequiredArgsConstructor
public class AppConfig {

    private final Environment environment;

    /**
     * Provides a Gson bean for JSON processing.
     *
     * @return A Gson instance configured for pretty printing and custom LocalDateTime handling.
     */
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Provides a Jdbi bean for database interactions.
     *
     * @return A Jdbi instance configured with database connection properties.
     */
    @Bean
    public Jdbi jdbi() {
        var jdbi = Jdbi.create(
                environment.getRequiredProperty("db.url"),
                environment.getRequiredProperty("db.username"),
                environment.getRequiredProperty("db.password")
        );

        return jdbi;
    }
}
