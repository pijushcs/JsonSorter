import org.json.JSONObject;

import java.util.*;
import java.lang.*;

public class ExampleUsage {
    public static void main(String[] args) {
        String jsonStr = "{\"name\": [ " +
                "{ \"id\": \"3\", \"name\": \"PQR\", \"add\": [{\"uid\": \"xyz\"},{\"uid\": \"abc\"}] }," +
                "{ \"id\": \"1\", \"name\": \"PQR\", \"add\": [{\"uid\": \"pqr\"},{\"uid\": \"pqr\", \"id\": \"1\"}] }," +
                "{ \"id\": \"2\", \"name\": \"ABC\" }]}";

        // Key to Unique id Map
        Map<String, String> mapKeyUniqueId = new TreeMap<String, String>();
        mapKeyUniqueId.put("name", "name,id");
        mapKeyUniqueId.put("name.add", "uid,id");

        // Create JsonSorter
        JsonSorter jsonSorter = new JsonSorter(jsonStr, mapKeyUniqueId);

        // Sort JSON string
        String sortedJsonStr = jsonSorter.sort();

        System.out.println("Sorted JSON Array with Name: " + sortedJsonStr);
    }
}
