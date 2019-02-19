package com.fh.util;

import com.google.gson.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Date;

public class JsonUtils {

    private static Gson gson;

    static {
        JsonSerializer<Date> dateJsonSerializer = new JsonSerializer<Date>() {
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };

        JsonDeserializer<Date> dateJsonDeserializer = new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : new Date(json.getAsLong());
            }
        };

        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, dateJsonSerializer)
                .registerTypeAdapter(Date.class, dateJsonDeserializer).create();
    }


    public static String toGsonJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T parseJson(Reader json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> T getPerson(String jsonString, Class<T> tClass) {
        return gson.fromJson(jsonString, tClass);
    }

    public static Object toBean(Object obj, Class voCalss) {
        JSONObject jsonObject = JSONObject.fromObject(obj);
        Object object = JSONObject.toBean(jsonObject, voCalss);
        return object;
    }

    public static Object toBean(String jsonStr, Class voCalss) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Object object = JSONObject.toBean(jsonObject, voCalss);
        return object;
    }

    public static String toJson(Object object) {
        JSONObject json = JSONObject.fromObject(object);
        return json.toString();
    }

    public static Object toArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();
    }

}