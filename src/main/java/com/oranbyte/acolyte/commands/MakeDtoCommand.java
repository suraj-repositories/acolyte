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

@CommandLine.Command(name = "make:dto", description = "Create a simple dto or pojo class")
public class MakeDtoCommand implements Runnable{

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the dto class")
    private String dtoName;


    @Override
    public void run() {
        String className = CaseConverter.toPascalCase(dtoName.replaceAll("[^a-zA-Z0-9_-]", ""));

        if (!className.endsWith("Dto")) {
            className += "Dto";
        }

        String basePackage = root.getContext().getBasePackage();
        String dtoPackage = basePackage + ".dto";
        String packagePath = dtoPackage.replace('.', '/');

        String templatePath = "templates/dto.template";
        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", dtoPackage);
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
                    ConsolePrinter.println("Dto created at: " + filePath);
                }
            }catch(IOException e){
                ConsolePrinter.error("Error creating dto: " + e.getMessage());
            }

        }else{
            ConsolePrinter.error("Error loading template or performing replacements.");
        }

    }
}
