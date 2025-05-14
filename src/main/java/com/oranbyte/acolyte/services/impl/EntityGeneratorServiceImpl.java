package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.EntityGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EntityGeneratorServiceImpl implements EntityGeneratorService {

    @Override
    public void generateEntity(String entityName, String basePackage) {
        String className = CaseConverter.toPascalCase(entityName.replaceAll("[^a-zA-Z0-9_-]", ""));
        if (className.endsWith("Entity") && !className.equals("Entity")) {
            className = className.substring(0, className.lastIndexOf("Entity"));
        }

        String entityPackage = basePackage + ".entity";
        String packagePath = entityPackage.replace('.', '/');
        String templatePath = "templates/entity.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", entityPackage);
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
                    ConsolePrinter.println("Entity created at: " + filePath);
                }
            } catch (IOException e) {
                ConsolePrinter.error("Error creating entity: " + e.getMessage());
            }
        } else {
            ConsolePrinter.error("Error loading template or performing replacements.");
        }
    }
}
