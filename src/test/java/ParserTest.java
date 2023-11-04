import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import org.example.PLSQLParser;
import org.example.PLSQLScanner;
import org.example.plsql.ast.Cursor;
import org.example.plsql.ast.Expression;
import org.example.plsql.ast.Program;
import org.example.plsql.ast.Statement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java_cup.runtime.Symbol;

public class ParserTest {

    private static final Type PROGRAM_TYPE = new TypeToken<Program>() {}.getType();

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
    void dosmth() {
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
            Path testPath = Path.of(url.getFile().substring(1), test);
            File inputFile = testPath.resolve("in.pls").toFile();
            File outputFile = testPath.resolve("out.json").toFile();

            try {
                FileReader reader = new FileReader(inputFile);

                ComplexSymbolFactory csf = new ComplexSymbolFactory();
                ScannerBuffer lexer = new ScannerBuffer(new PLSQLScanner(new BufferedReader(reader), csf));
                PLSQLParser parser = new PLSQLParser(lexer, csf);

                Symbol output = parser.parse();

                Assertions.assertTrue(output.value instanceof Program);

                Program program = (Program) output.value;

                System.out.println(gson.toJson(program, PROGRAM_TYPE));

                JsonReader outputReader = new JsonReader(new FileReader(outputFile));

                Program expected = gson.fromJson(outputReader, PROGRAM_TYPE);

                Assertions.assertTrue(expected.areEqual(program));
            } catch (FileNotFoundException exception) {
                Assertions.fail("Invalid test resources folder structure - folder \"" + testPath + "\" must contain:\n" +
                        " - `in.pls` file, which contains parser input;\n" +
                        " - `out.json` file, which contains expected AST output of parser.");
            } catch (JsonIOException | JsonSyntaxException exception) {
                Assertions.fail("Failed to parse \"" + testPath.resolve("out.json") + "\" file." + exception);
            } catch (Exception exception) {
                exception.printStackTrace();
                Assertions.fail("Failed to parse \"" + testPath.resolve("in.pls") + "\" file. " + exception);
            }
        }
    }
}
