import com.google.gson.*;
import org.example.oracle.ast.*;

import java.lang.reflect.Type;

public class Utils {


    public static final Type PROGRAM_TYPE = new com.google.gson.reflect.TypeToken<Program>() {
    }.getType();

    private static class SerdePreservingClass implements JsonSerializer<Object>, JsonDeserializer<Object> {
        private static final String CLASS_META_KEY = "class";

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObj = jsonElement.getAsJsonObject();
            String className = jsonObj.get(CLASS_META_KEY).getAsString();
            try {
                Class<?> clz = Class.forName(className);
                return jsonDeserializationContext.deserialize(jsonElement, clz);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        @Override
        public JsonElement serialize(Object obj, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonElement jsonElement = jsonSerializationContext.serialize(obj, obj.getClass());
            jsonElement.getAsJsonObject().addProperty(CLASS_META_KEY, obj.getClass().getName());
            return jsonElement;
        }
    }

    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Statement.class, new SerdePreservingClass())
                .registerTypeAdapter(Expression.class, new SerdePreservingClass())
                .registerTypeAdapter(Cursor.class, new SerdePreservingClass())
                .registerTypeAdapter(Declaration.class, new SerdePreservingClass())
                .registerTypeAdapter(TypeToken.class, new SerdePreservingClass())
                .registerTypeAdapter(SubtypeDefinition.Constraint.class, new SerdePreservingClass())
                .create();
    }
}
