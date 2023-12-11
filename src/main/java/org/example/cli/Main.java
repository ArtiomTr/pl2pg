package org.example.cli;

import org.example.postgres.generation.GenerateException;
import org.example.postgres.generation.Generator;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "pl2pg", mixinStandardHelpOptions = true,
        description = "Converts Oracle PL/SQL programs to PostgreSQL PL/pgSQL", version = "0.1")
public class Main implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "File containing PL/SQL program")
    private File inputFile;

//    @CommandLine.Option(names = {"-o", "--output"}, description = "Output file")
//    private File outputFile;

    @Override
    public Integer call() throws Exception {
        Generator generator = new Generator();
        try {
            String code = generator.generate(new BufferedReader(new FileReader(inputFile)));
            System.out.println(code);
            return 0;
        } catch (GenerateException e) {
            System.err.println("Failed to convert PL/SQL program");
            e.printStackTrace();
            return 1;
        }
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
