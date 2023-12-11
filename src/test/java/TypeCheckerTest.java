import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.example.oracle.ast.Program;
import org.example.oracle.main.ParseException;
import org.example.oracle.main.Parser;
import org.example.oracle.main.TypeChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class TypeCheckerTest {

    @Test
    void typeCheckVariousInputs() {
        URL url = this.getClass().getResource("/type_checker_tests");
        Assertions.assertNotNull(url);
        File testsFolder = new File(url.getFile());
        String[] tests = Arrays.stream(Objects.requireNonNull(testsFolder.list())).sorted().toArray(String[]::new);
        Assertions.assertNotNull(tests);
        Assertions.assertTrue(tests.length > 0);

        Gson gson = Utils.createGson();

        for (String test : tests) {
            File testFolder = new File(url.getFile());

            if (!testFolder.isDirectory()) {
                continue;
            }

            Path testPath = Path.of(testFolder.getPath(), test);
            File inputFile = testPath.resolve("in.pls").toFile();
            File outputFile = testPath.resolve("out.json").toFile();

            Boolean expected = null;

            try (JsonReader reader = new JsonReader(new FileReader(outputFile))) {
                expected = gson.fromJson(reader, Boolean.class);
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
                Assertions.fail("Failed to parse file \"" + testPath.resolve("in.pls") + "\". " + e);
            }

            try {
                TypeChecker typeChecker = new TypeChecker();
                typeChecker.doTypeCheck(received);

                if (!expected) {
                    Assertions.fail("Type checker expected to fail, but instead, it passed.");
                }
            } catch (Exception e) {
                if (expected) {
                    e.printStackTrace();
                    Assertions.fail("Type checker expected to pass - instead, it failed with exception" + e);
                }
            }
        }
    }
}
