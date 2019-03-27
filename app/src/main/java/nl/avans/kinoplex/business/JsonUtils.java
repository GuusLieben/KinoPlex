package nl.avans.kinoplex.business;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class JsonUtils<T> {

    public T parseToObject(String json) {
        // Use Gson to parse a JSON string into an object, don't touch this or I'll stab you -GL
        Type t = new TypeToken<T>() {
        }.getType();
        return new Gson().fromJson(json, t);
    }

}
