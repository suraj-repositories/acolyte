package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.utils.TemplateUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CommandLine.Command(name = "make:controller", description = "Create a new simple controller")
public class MakeControllerCommand implements Runnable {

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the controller")
    private String controllerName;

    @Override
    public void run() {
        // Clean the controller name and append "Controller" if not already there
        String className = controllerName.replaceAll("[^a-zA-Z0-9]", "");
        if (!className.endsWith("Controller")) {
            className += "Controller";
        }

        // Get base package name
        String basePackage = root.getContext().getBasePackage();
        String controllerPackage = basePackage + ".controllers";
        String packagePath = controllerPackage.replace('.', '/');

        // Template file path (inside the jar)
        String templatePath = "templates/controller.template";

        // Replace map
        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", controllerPackage);
        replacements.put("endpoint", className.toLowerCase().replace("controller", ""));
        replacements.put("className", className);

        // Load and apply template
        String content = TemplateUtils.loadTemplate(templatePath, replacements);

        if (content != null) {
            String filePath = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packagePath + "/" + className + ".java";
            File file = new File(filePath);

            try {
                file.getParentFile().mkdirs();
                if (file.createNewFile()) {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(content);
                    }
                    System.out.println("✅ Controller created at: " + filePath);
                } else {
                    System.out.println("⚠️ File already exists: " + filePath);
                }
            } catch (IOException e) {
                System.err.println("❌ Error creating controller: " + e.getMessage());
            }
        } else {
            System.err.println("❌ Error loading template or performing replacements.");
        }
    }
}
