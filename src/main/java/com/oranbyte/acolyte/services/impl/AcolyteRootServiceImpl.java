package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.constants.ConsoleColor;
import com.oranbyte.acolyte.services.AcolyteRootService;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;
import picocli.CommandLine;

import java.util.Collections;

public class AcolyteRootServiceImpl implements AcolyteRootService {

    @Override
    public void printBanner() {
        String content = TemplateUtils.loadTemplate("templates/banner.template", Collections.emptyMap());
        ConsolePrinter.success(content);
    }

    @Override
    public void printVersionString() {
        String versionString = ConsolePrinter.builder()
                .addText(AppConstants.APP_NAME, ConsoleColor.GREEN)
                .addText(" ", ConsoleColor.DEFAULT)
                .addText("Version", ConsoleColor.DEFAULT)
                .addText(" ", ConsoleColor.DEFAULT)
                .addText(AppConstants.APP_VERSION, ConsoleColor.CYAN)
                .addText(" \uD83C\uDF4A ", ConsoleColor.DEFAULT)
                .addText(AppConstants.APP_LAST_UPDATED_AT, ConsoleColor.PURPLE)
                .build();

        System.out.println(versionString);
    }

    @Override
    public void printCurrentCommandDetails(AcolyteCommandRoot root) {
        CommandLine cmd = new CommandLine(root);
        cmd.getHelpSectionMap().remove(CommandLine.Model.UsageMessageSpec.SECTION_KEY_COMMAND_LIST);
        cmd.usage(System.out, CommandLine.Help.Ansi.ON);
    }

    @Override
    public void printCommandList() {
        CommandLine root = new CommandLine(new AcolyteCommandRoot());

        for (CommandLine sub : root.getSubcommands().values()) {
            CommandLine.Model.CommandSpec spec = sub.getCommandSpec();
            String name = spec.name();
            String desc = String.join(" ", spec.usageMessage().description());
            System.out.printf("%s%-30s%s %s%n", ConsoleColor.GREEN.code(), name, ConsoleColor.DEFAULT.code(), desc);
        }
    }




}
