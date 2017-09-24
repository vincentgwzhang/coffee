package tina.coffee.function;

import com.google.gson.Gson;

public class JsonFunction {

    /**
     * Put one object in JSON string
     */
    public static String toJson(Object srcObject) {
        Gson gson = new Gson();
        return gson.toJson(srcObject);
    }
}
