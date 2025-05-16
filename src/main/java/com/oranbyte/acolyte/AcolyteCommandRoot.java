package com.oranbyte.acolyte;

import com.oranbyte.acolyte.commands.*;
import com.oranbyte.acolyte.constants.AcolyteContext;
import com.oranbyte.acolyte.constants.ConsoleColor;
import com.oranbyte.acolyte.services.AcolyteRootService;
import com.oranbyte.acolyte.services.impl.AcolyteRootServiceImpl;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.ProjectMetadataUtil;
import picocli.CommandLine;
import picocli.CommandLine.Help.Ansi;

import java.io.PrintWriter;

@CommandLine.Command(
        name = "acolyte",
        mixinStandardHelpOptions = true,
        subcommands = {
                MakeControllerCommand.class,
                MakeServiceCommand.class,
                MakeEntityCommand.class,
                MakeViewCommand.class,
                MakeDtoCommand.class,
                MakeConfigurationCommand.class,
                MakeRepositoryCommand.class,
                MakeValidationCommand.class,
        }
)
public class AcolyteCommandRoot implements Runnable {
    private AcolyteContext context;

    @CommandLine.Option(names = {"-v", "--version", "-version", "-V"}, description = "Get the version application")
    private boolean version;

    public AcolyteCommandRoot() {
        String basePackage = ProjectMetadataUtil.getBasePackage(System.getProperty("user.dir"));
        this.context = new AcolyteContext(basePackage);
    }

    public AcolyteContext getContext() {
        return context;
    }

    @Override
    public void run() {
        AcolyteRootService acolyteRootService = new AcolyteRootServiceImpl();

        if(version){
            acolyteRootService.printVersionString();
            return;
        }

        acolyteRootService.printBanner();
        acolyteRootService.printVersionString();

        System.out.println();
        acolyteRootService.printCurrentCommandDetails(this);
        acolyteRootService.printCommandList();

    }
}
