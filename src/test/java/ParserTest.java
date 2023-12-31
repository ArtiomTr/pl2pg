import com.google.gson.stream.JsonReader;
import com.google.gson.Gson;
import org.example.oracle.ast.*;
import org.example.oracle.main.ParseException;
import org.example.oracle.main.Parser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;


public class ParserTest {

    @Test
    void parseVariousInputs() {
        URL url = this.getClass().getResource("/parser_tests");
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

            Path testPath = Paths.get(testFolder.getPath(), test);
            File inputFile = testPath.resolve("in.pls").toFile();
            File outputFile = testPath.resolve("out.json").toFile();

            Program expected = null;

            try (JsonReader reader = new JsonReader(new FileReader(outputFile))) {
                expected = gson.fromJson(reader, Utils.PROGRAM_TYPE);
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
                Assertions.assertEquals(expected, received, "Expected parser to fail, but instead, it returned " + gson.toJson(received, Utils.PROGRAM_TYPE));
            } else {
                assertThat(received).usingRecursiveComparison().isEqualTo(expected);
            }
        }
    }
}
