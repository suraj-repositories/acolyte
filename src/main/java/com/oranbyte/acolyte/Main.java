package com.oranbyte.acolyte;

import com.oranbyte.acolyte.commands.MakeControllerCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

public class Main {

    public static void main(String[] args) {
        new CommandLine(new AcolyteCommandRoot()).execute(args);
    }
}
