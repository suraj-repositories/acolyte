package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.DtoGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DtoGeneratorServiceImpl implements DtoGeneratorService {

    @Override
    public void generateDto(String dtoName, String basePackage) {
        String className = CaseConverter.toPascalCase(dtoName.replaceAll("[^a-zA-Z0-9_-]", ""));
        className = CaseConverter.appendIfNotAvailable(className, "Dto");

        String dtoPackage = basePackage + ".dto";
        String packagePath = dtoPackage.replace('.', '/');
        String templatePath = "templates/dto.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", dtoPackage);
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
                    ConsolePrinter.println("Dto created at: " + filePath);
                }
            } catch (IOException e) {
                ConsolePrinter.error("Error creating dto: " + e.getMessage());
            }

        } else {
            ConsolePrinter.error("Error loading template or performing replacements.");
        }
    }
}
