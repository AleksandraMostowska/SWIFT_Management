package mostowska.aleksandra.config.appConfig;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mostowska.aleksandra.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class GsonBeanTest {

    private Gson gson;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        gson = context.getBean(Gson.class);
    }

    @Test
    void gsonShouldBeConfiguredCorrectly() {
        assertNotNull(gson, "Gson bean should not be null");
    }

    @Test
    void gsonShouldFormatJsonWithPrettyPrinting() {
        var object = new TestPerson("John", "Doe", 30);
        var json = gson.toJson(object);
        assertTrue(json.contains("\n"), "JSON should be pretty-printed");
    }

    @Test
    void gsonShouldDeserializeJsonCorrectly() {
        var json = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":30}";
        var person = gson.fromJson(json, TestPerson.class);
        assertNotNull(person);
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals(30, person.getAge());
    }

    @Test
    void gsonShouldSerializeToJsonCorrectly() {
        var person = new TestPerson("Jane", "Doe", 25);
        var json = gson.toJson(person);
        assertNotNull(json);
        assertTrue(json.contains("\"firstName\": \"Jane\""));
        assertTrue(json.contains("\"lastName\": \"Doe\""));
        assertTrue(json.contains("\"age\": 25"));
    }

    @Getter
    @AllArgsConstructor
    static class TestPerson {
        private String firstName;
        private String lastName;
        private int age;
    }
}

