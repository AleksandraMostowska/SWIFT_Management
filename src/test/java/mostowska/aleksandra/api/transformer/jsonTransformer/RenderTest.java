package mostowska.aleksandra.api.transformer.jsonTransformer;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.XSlf4j;
import mostowska.aleksandra.api.transformer.JsonTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RenderTest {
    private JsonTransformer jsonTransformer;

    @BeforeEach
    void setUp() {
        jsonTransformer = new JsonTransformer(new Gson());
    }

    @Test
    @DisplayName("Should correctly serialize object to JSON")
    void shouldSerializeObjectToJson() throws Exception {
        var person = new TestPerson("Jane", "Doe", 25);
        var json = jsonTransformer.render(person);

        assertNotNull(json);
        assertTrue(json.contains("\"firstName\":\"Jane\""));
        assertTrue(json.contains("\"lastName\":\"Doe\""));
        assertTrue(json.contains("\"age\":25"));
    }

    @Test
    @DisplayName("Should return null for null object")
    void shouldReturnNullForNullObject() throws Exception {
        var json = jsonTransformer.render(null);
        assertEquals("null", json);
    }

    @Getter
    @AllArgsConstructor
    static class TestPerson {
        private String firstName;
        private String lastName;
        private int age;
    }


}
