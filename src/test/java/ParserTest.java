import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.JsonSerializationContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;
import org.example.plsql.ast.*;
import org.example.plsql.main.ParseException;
import org.example.plsql.main.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;


public class ParserTest {

    private static final Type PROGRAM_TYPE = new com.google.gson.reflect.TypeToken<Program>() {
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

    @Test
    void parseVariousInputs() {
        URL url = this.getClass().getResource("/parser_tests");
        Assertions.assertNotNull(url);
        File testsFolder = new File(url.getFile());
        String[] tests = Arrays.stream(Objects.requireNonNull(testsFolder.list())).sorted().toArray(String[]::new);
        Assertions.assertNotNull(tests);
        Assertions.assertTrue(tests.length > 0);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Statement.class, new SerdePreservingClass())
                .registerTypeAdapter(Expression.class, new SerdePreservingClass())
                .registerTypeAdapter(Cursor.class, new SerdePreservingClass())
                .registerTypeAdapter(Declaration.class, new SerdePreservingClass())
                .registerTypeAdapter(TypeToken.class, new SerdePreservingClass())
                .registerTypeAdapter(SubtypeDefinition.Constraint.class, new SerdePreservingClass())
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
                assertThat(received).usingRecursiveComparison().isEqualTo(expected);
            }
        }
    }
}
