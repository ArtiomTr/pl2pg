import org.example.postgres.generation.GenerateException;
import org.example.postgres.generation.Generator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class GeneratorTest {

    @Test
    void generateVariousPrograms() {
        URL url = this.getClass().getResource("/generator_tests");
        Assertions.assertNotNull(url);
        File testsFolder = new File(url.getFile());
        String[] tests = Arrays.stream(Objects.requireNonNull(testsFolder.list())).sorted().toArray(String[]::new);
        Assertions.assertNotNull(tests);
        Assertions.assertTrue(tests.length > 0);

        for (String test : tests) {
            File testFolder = new File(url.getFile());

            if (!testFolder.isDirectory()) {
                continue;
            }

            Path testPath = Paths.get(testFolder.getPath(), test);
            File inputFile = testPath.resolve("in.pls").toFile();
            Path outputPath = testPath.resolve("out.psql");

            String expected = null;

            try {
                expected = new String(Files.readAllBytes(outputPath));
            } catch (FileNotFoundException exception) {
                Assertions.fail("Invalid test resources folder structure - folder \"" + testPath + "\" must contain:\n" +
                        " - `in.pls` file, which contains generator input;\n" +
                        " - `out.psql` file, which contains expected PostgreSQL program.\n" +
                        "File `" + outputPath + "` is missing.");
            } catch (IOException exception) {
                Assertions.fail("Failed to read \"" + outputPath + "\". Unexpected error occurred:\n" +
                        "    " + exception);
            }

            String received = null;
            try (FileReader reader = new FileReader(inputFile)) {
                Generator generator = new Generator();
                received = generator.generate(reader);
            } catch (FileNotFoundException exception) {
                Assertions.fail("Invalid test resources folder structure - folder \"" + testPath + "\" must contain:\n" +
                        " - `in.pls` file, which contains parser input;\n" +
                        " - `out.psql` file, which contains expected AST output of parser." +
                        "File `in.pls` is missing.");
            } catch (IOException exception) {
                Assertions.fail("Failed to read \"" + testPath.resolve("in.pls") + "\". Unexpected error occurred:\n" +
                        "    " + exception);
            } catch (GenerateException e) {
                if (!expected.equals("null")) {
                    e.printStackTrace();
                    Assertions.fail("Failed to parse file \"" + testPath.resolve("in.pls") + "\". " + e);
                }
            }

            if (expected.equals("null")) {
                Assertions.assertEquals(expected, received, "Expected generator to fail, but instead, it returned " + received);
            } else {
                Assertions.assertEquals(expected, received);
            }
        }
    }

}
