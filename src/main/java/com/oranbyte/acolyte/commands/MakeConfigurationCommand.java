package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CommandLine.Command(name = "make:configuration", description = "Create a simple configuration class")
public class MakeConfigurationCommand implements Runnable{

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the configuration class")
    private String configName;


    @Override
    public void run() {
        String className = CaseConverter.toPascalCase(configName.replaceAll("[^a-zA-Z0-9_-]", ""));

        if (!className.endsWith("Configuration")) {
            className += "Configuration";
        }

        String basePackage = root.getContext().getBasePackage();
        String configPackage = basePackage + ".configuration";
        String packagePath = configPackage.replace('.', '/');

        String templatePath = "templates/configuration.template";
        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", configPackage);
        replacements.put("className", className);

        String content = TemplateUtils.loadTemplate(templatePath, replacements);

        if(content != null){
            String filePath = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packagePath + "/" + className + ".java";
            File file = new File(filePath);

            try{
                file.getParentFile().mkdirs();

                if(file.exists()){
                    ConsolePrinter.error("File already exists: " + filePath);
                }else{
                    try(FileWriter writer = new FileWriter(file)){
                        writer.write(content);
                    }
                    ConsolePrinter.println("Configuration created at: " + filePath);
                }
            }catch(IOException e){
                ConsolePrinter.error("Error creating configuration: " + e.getMessage());
            }

        }else{
            ConsolePrinter.error("Error loading template or performing replacements.");
        }

    }
}
