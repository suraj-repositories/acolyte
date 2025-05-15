package com.oranbyte.acolyte;

import com.oranbyte.acolyte.commands.*;
import com.oranbyte.acolyte.constants.AcolyteContext;
import com.oranbyte.acolyte.utils.ProjectMetadataUtil;
import picocli.CommandLine;

@CommandLine.Command(
    name = "acolyte",
    subcommands = {
            MakeControllerCommand.class,
            MakeServiceCommand.class,
            MakeEntityCommand.class,
            MakeViewCommand.class,
            MakeDtoCommand.class,
            MakeConfigurationCommand.class,
            MakeRepositoryCommand.class,
    }
)
public class AcolyteCommandRoot implements Runnable {
    private AcolyteContext context;

    public AcolyteCommandRoot() {
        String basePackage = ProjectMetadataUtil.getBasePackage(System.getProperty("user.dir"));
        this.context = new AcolyteContext(basePackage);
    }

    public AcolyteContext getContext() {
        return context;
    }

    @Override
    public void run() {
        System.out.println("Acolyte CLI");
    }
}
