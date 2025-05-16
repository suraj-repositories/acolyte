package com.oranbyte.acolyte;

import picocli.CommandLine;

public class Main {

    public static void main(String[] args) {
       int exitCode = new CommandLine(new AcolyteCommandRoot()).execute(args);

        // testing...

//        int exitCode = new CommandLine(new AcolyteCommandRoot()).execute("make:validator", "Custom", "--dto=Abc_def");
//        new CommandLine(new AcolyteCommandRoot()).execute("make:repository", "supersonic-suatomic_java", "--id=Long");

        System.exit(exitCode);

    }
}
