package com.oranbyte.acolyte.commands;


import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CommandLine.Command(name = "make:view", description = "Create a simple view class")
public class MakeViewCommand implements Runnable{


    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the view file")
    private String viewName;

    @Override
    public void run() {
        String viewFileName = getThymeleafViewFileName(viewName);

        String templatePath = "templates/view.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("viewFileName", viewFileName);

        String content = TemplateUtils.loadTemplate(templatePath, replacements);

        if(content != null){
            String filePath = AppConstants.PROJECT_DIRECTORY + "/src/main/resources/templates/" + viewFileName + ".html";
            File file = new File(filePath);

            try{
                file.getParentFile().mkdirs();
                if(file.exists()){
                    ConsolePrinter.error("File already exists: " + filePath);
                }else{
                    try(FileWriter writer = new FileWriter(file)){
                        writer.write(content);
                    }
                    ConsolePrinter.println("Entity created at: " + filePath);
                }
            }catch(IOException e){
                ConsolePrinter.error("Error creating entity: " + e.getMessage());
            }
        }else{
            ConsolePrinter.error("Error loading template or performing replacements.");
        }

    }

    public static String getThymeleafViewFileName(String viewName) {
        return toDashSeparated(viewName.replaceAll("[^a-zA-Z0-9_]", ""));
    }

    public static String toDashSeparated(String input) {
        String result = input.replaceAll("_", "-");
        result = result.replaceAll("(?<!^)([A-Z])", "-$1");
        return result.toLowerCase();
    }


}
