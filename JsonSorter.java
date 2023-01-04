import java.util.*;
import java.lang.*;
import org.json.*;

/**
 * Recursive JsonSorter. To use JsonSorter, follow the steps below:
 *  - Create a Map (preferably TreeMap) with 'json-key' to 'unique-id' map.
 *  - Create a JsonSorter object with the map and the json string
 *  - Call jsonSorter.sort().
 * Check ExampleUsage for more usage details.
 */
public class JsonSorter {
    String jsonStr;
    Map<String, String> jsonKeyMap;

    public JsonSorter (String jsonStr, Map<String, String> jsonKeyMap) {
        this.jsonStr = jsonStr;
        this.jsonKeyMap = jsonKeyMap;
    }

    private JSONObject jsonSorterRecursive(JSONObject jsonObject, String prefix) {
        if (jsonObject.isEmpty()) {
            return new JSONObject("{}");
        }

        Set<String> jsonKeys = jsonObject.keySet();

        for(String jsonKey : jsonKeys) {
            if(jsonKeyMap.containsKey(prefix + jsonKey)) {
                JSONArray jsonArray = jsonObject.getJSONArray(jsonKey);
                JSONArray sortedJsonArray = new JSONArray();

                List<JSONObject> list = new ArrayList<JSONObject>();
                for(int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.getJSONObject(i));
                }

                list.sort((Comparator) (o1, o2) -> {
                    String str1 = new String();
                    String str2 = new String();
                    try {
                        JSONObject x = (JSONObject) o1;
                        JSONObject y = (JSONObject) o2;

                        str1 = (String) x.get(jsonKeyMap.get(prefix + jsonKey));
                        str2 = (String) y.get(jsonKeyMap.get(prefix + jsonKey));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return str1.compareTo(str2);
                });

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject listObject = jsonSorterRecursive(list.get(i), prefix + jsonKey + ".");
                    sortedJsonArray.put(listObject);
                }

                jsonObject = jsonObject.put(jsonKey, sortedJsonArray);
            }
        }

        return jsonObject;
    }

    public String sort() {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonSorterRecursive(jsonObject,"").toString();
    }
}
