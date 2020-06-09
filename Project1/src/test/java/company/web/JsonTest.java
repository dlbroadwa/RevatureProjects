/*package company.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLOutput;

public class JsonTest extends TestCase {
    private String testJson= "{\"car\":\"honda\",\"wheels\":\"4\"}";

    @Test
    public void testToJson() {
        TestObject object = new TestObject();
        object.setName("Ford");
        JsonNode node = Json.toJson(object);

        assertEquals(node.get("name").asText() ,"Ford");
    }

    @Test
    public void testParse() throws IOException {
        JsonNode node = Json.parse(testJson);
        assertEquals(node.get("car").asText(),"honda");
    }

    @Test
    public void testToString() throws JsonProcessingException {
        TestObject object = new TestObject();
        object.setName("Ford");
        JsonNode node = Json.toJson(object);

        System.out.println(Json.toString(node));
        System.out.println(Json.toBetterString(node));
    }

    @Test
    public void testFromJson() throws IOException {
        JsonNode node = Json.parse(testJson);

        TestObject object = Json.fromJson(node, TestObject.class);

        //assertEquals(object.getName(),"honda");
    }
}*/