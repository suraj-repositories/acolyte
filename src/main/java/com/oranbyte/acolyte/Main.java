package com.oranbyte.acolyte;

import com.oranbyte.acolyte.commands.MakeControllerCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

public class Main {

    public static void main(String[] args) {
//        new CommandLine(new AcolyteCommandRoot()).execute(args);

        // testing...
//        new CommandLine(new AcolyteCommandRoot()).execute("make:repository", "supersonic-suatomic_java", "--id=Long");

        System.out.println(
                """
                    _  
                   / \\     ______
                  / _ \\   / ____/
                 / ___ \\ / /___
                /_/   \\_\\\\____/
                            
            """
        );

    }
}
