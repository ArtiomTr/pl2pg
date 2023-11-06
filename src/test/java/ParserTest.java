import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.example.plsql.ast.Cursor;
import org.example.plsql.ast.Expression;
import org.example.plsql.ast.Program;
import org.example.plsql.ast.Statement;
import org.example.plsql.main.ParseException;
import org.example.plsql.main.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;

public class ParserTest {

    private static final Type PROGRAM_TYPE = new TypeToken<Program>() {
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
            jsonElement.getAsJsonObject().addProperty(CLASS_META_KEY, obj.getClass().getCanonicalName());
            return jsonElement;
        }
    }

    @Test
    void parseVariousInputs() {
        URL url = this.getClass().getResource("/parser_tests");
        Assertions.assertNotNull(url);
        File testsFolder = new File(url.getFile());
        String[] tests = testsFolder.list();
        Assertions.assertNotNull(tests);
        Assertions.assertTrue(tests.length > 0);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Statement.class, new SerdePreservingClass())
                .registerTypeAdapter(Expression.class, new SerdePreservingClass())
                .registerTypeAdapter(Cursor.class, new SerdePreservingClass())
                .create();

        for (String test : tests) {
            File testFolder = new File(url.getFile());

            if (!testFolder.isDirectory()) {
                continue;
            }

            Path testPath = Path.of(testFolder.getPath(), test);
            File inputFile = testPath.resolve("in.pls").toFile();
            File outputFile = testPath.resolve("out.json").toFile();

            Program expected = null;

            try (JsonReader reader = new JsonReader(new FileReader(outputFile))) {
                expected = gson.fromJson(reader, PROGRAM_TYPE);
            } catch (FileNotFoundException exception) {
                Assertions.fail("Invalid test resources folder structure - folder \"" + testPath + "\" must contain:\n" +
                        " - `in.pls` file, which contains parser input;\n" +
                        " - `out.json` file, which contains expected AST output of parser.\n" +
                        "File `" + testPath.resolve("out.json") + "` is missing.");
            } catch (IOException exception) {
                Assertions.fail("Failed to read \"" + testPath.resolve("out.json") + "\". Unexpected error occurred:\n" +
                        "    " + exception);
            }

            Program received = null;
            try (FileReader reader = new FileReader(inputFile)) {
                Parser parser = new Parser();
                received = parser.parse(reader);
            } catch (FileNotFoundException exception) {
                Assertions.fail("Invalid test resources folder structure - folder \"" + testPath + "\" must contain:\n" +
                        " - `in.pls` file, which contains parser input;\n" +
                        " - `out.json` file, which contains expected AST output of parser." +
                        "File `in.pls` is missing.");
            } catch (IOException exception) {
                Assertions.fail("Failed to read \"" + testPath.resolve("in.pls") + "\". Unexpected error occurred:\n" +
                        "    " + exception);
            } catch (ParseException e) {
                if (expected != null) {
                    Assertions.fail("Failed to parse file \"" + testPath.resolve("in.pls") + "\". " + e);
                }
            }

            if (expected == null) {
                Assertions.assertEquals(expected, received, "Expected parser to fail, but instead, it returned " + gson.toJson(received, PROGRAM_TYPE));
            } else {
                Assertions.assertTrue(expected.areEqual(received), "Expected parser to return " + gson.toJson(expected, PROGRAM_TYPE) + ", but instead it returned " + gson.toJson(received, PROGRAM_TYPE));
            }
        }
    }
}
