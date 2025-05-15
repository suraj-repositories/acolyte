package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.RepositoryGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RepositoryGeneratorServiceImpl implements RepositoryGeneratorService {

    @Override
    public void generateRepository(String inputName, String basePackage, String idType) {

        String cleanName = CaseConverter.toPascalCase(inputName.replaceAll("[^a-zA-Z0-9_-]", ""));
        String className = cleanName.endsWith("Repository")
                        && !cleanName.equals("Repository")
                        ? cleanName.substring(0, cleanName.lastIndexOf("Repository"))
                        : cleanName;

        String repositoryClassName = className + "Repository";
        String repositoryPackage = basePackage + ".repository";
        String entityPackage = basePackage + ".entity";
        String packagePath = repositoryPackage.replace('.', '/');
        String templatePath = "templates/repository.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", repositoryPackage);
        replacements.put("className", className);
        replacements.put("idType", idType);
        replacements.put("entityPackage", entityPackage);

        String content = TemplateUtils.loadTemplate(templatePath, replacements);

        if (content != null) {
            String filePath = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packagePath + "/" + repositoryClassName + ".java";
            File file = new File(filePath);

            try {
                file.getParentFile().mkdirs();
                if (file.exists()) {
                    ConsolePrinter.error("File already exists: " + filePath);
                } else {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(content);
                    }
                    ConsolePrinter.println("Repository created at: " + filePath);
                }
            } catch (IOException e) {
                ConsolePrinter.error("Error creating repository: " + e.getMessage());
            }
        } else {
            ConsolePrinter.error("Error loading template or performing replacements.");
        }
    }


}
