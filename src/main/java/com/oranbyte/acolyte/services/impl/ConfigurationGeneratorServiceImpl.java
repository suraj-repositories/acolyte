package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.ConfigurationGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationGeneratorServiceImpl implements ConfigurationGeneratorService {

    @Override
    public void generateConfiguration(String configName, String basePackage) {
        String className = CaseConverter.appendIfNotAvailable(
                CaseConverter.toClassName(configName)
                , "Configuration");

        String configPackage = basePackage + ".configuration";
        String packagePath = configPackage.replace('.', '/');
        String templatePath = "templates/configuration.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", configPackage);
        replacements.put("className", className);

        String content = TemplateUtils.loadTemplate(templatePath, replacements);

        if (content != null) {
            String filePath = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packagePath + "/" + className + ".java";
            File file = new File(filePath);

            try {
                file.getParentFile().mkdirs();

                if (file.exists()) {
                    ConsolePrinter.error("File already exists: " + filePath);
                } else {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(content);
                    }
                    ConsolePrinter.println("Configuration created at: " + filePath);
                }
            } catch (IOException e) {
                ConsolePrinter.error("Error creating configuration: " + e.getMessage());
            }

        } else {
            ConsolePrinter.error("Error loading template or performing replacements.");
        }
    }
}
