package company.web;
import com.fasterxml.jackson.core.*;


import com.fasterxml.jackson.*;



import java.io.IOException;

public class Json {
  /*  private  static final ObjectMapper objectMapper = getDefaultObjectMapper();
    private  static ObjectMapper getDefaultObjectMapper(){
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return defaultObjectMapper;
    }

    public static JsonNode toJson(Object o) {
        return objectMapper.valueToTree(o);
    }

    public static JsonNode parse(String string) throws IOException{
        return objectMapper.readTree(string);
    }

    public static String toString(JsonNode node) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        return getString(node, false);
    }

    public static String toBetterString(JsonNode node) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        return getString(node, true);
    }

    private static String getString(JsonNode node,boolean tf) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        if(tf)
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        return objectWriter.writeValueAsString(node);
    }

    public static <A> A fromJson(JsonNode node , Class<A> a ) throws JsonProcessingException {
        return objectMapper.treeToValue(node, a);
    }

*/
}
