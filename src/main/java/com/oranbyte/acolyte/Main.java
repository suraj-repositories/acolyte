package com.oranbyte.acolyte;

import com.oranbyte.acolyte.commands.MakeControllerCommand;
import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.constants.ConsoleColor;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;
import io.quarkus.qute.Template;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {
//       int exitCode = new CommandLine(new AcolyteCommandRoot()).execute(args);

        // testing...

        int exitCode = new CommandLine(new AcolyteCommandRoot()).execute("-v");
//        new CommandLine(new AcolyteCommandRoot()).execute("make:repository", "supersonic-suatomic_java", "--id=Long");


        System.exit(exitCode);

    }
}
