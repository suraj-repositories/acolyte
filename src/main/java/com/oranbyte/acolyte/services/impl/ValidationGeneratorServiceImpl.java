package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.DtoGeneratorService;
import com.oranbyte.acolyte.services.ValidationGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ValidationGeneratorServiceImpl implements ValidationGeneratorService {

    private static final String VALIDATOR_TEMPLATE = "templates/validator-impl.template";
    private static final String ANNOTATION_TEMPLATE = "templates/validator-annotation.template";

    @Override
    public void generate(String validatorName, String dtoClass, String basePackage) {
        String validatorBaseName = sanitizeValidatorBaseName(validatorName);
        String annotationName = "Valid" + validatorBaseName;
        String validatorClassName = validatorBaseName + "Validator";
        String validatorPackage = basePackage + ".validation";

        dtoClass = CaseConverter.appendIfNotAvailable(
                CaseConverter.toPascalCase(dtoClass.replaceAll("[^a-zA-Z0-9_-]", "")), "Dto"
        );

        String dtoFullClassName = resolveDtoFullClassName(dtoClass, basePackage);
        ensureDtoExists(dtoFullClassName, dtoClass, basePackage);

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", validatorPackage);
        replacements.put("annotationName", annotationName);
        replacements.put("validatorClassName", validatorClassName);
        replacements.put("dtoClass", dtoClass);
        replacements.put("dtoFullClassName", dtoFullClassName);

        writeFile(ANNOTATION_TEMPLATE, annotationName + ".java", validatorPackage, replacements);
        writeFile(VALIDATOR_TEMPLATE, validatorClassName + ".java", validatorPackage + ".impl", replacements);
    }

    private String sanitizeValidatorBaseName(String name) {
        String className = CaseConverter.toClassName(name.replaceAll("[^a-zA-Z0-9_-]", ""));
        if (className.endsWith("Validator")) {
            className = className.substring(0, className.lastIndexOf("Validator"));
        }
        if (className.startsWith("Valid")) {
            className = className.substring("Valid".length());
        }
        return className;
    }

    private String resolveDtoFullClassName(String dtoClass, String basePackage) {
        String className = dtoClass.replace('/', '.').replaceAll("\\.+", ".");
        if (className.endsWith(".")) {
            className = className.substring(0, className.length() - 1);
        }
        if (!className.contains(".")) {
            className = basePackage + ".dto." + className;
        }
        return className;
    }

    private void ensureDtoExists(String fullClassName, String simpleClassName, String basePackage) {
        String path = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + fullClassName.replace('.', '/') + ".java";
        File dtoFile = new File(path);

        if (!dtoFile.exists()) {
            ConsolePrinter.info("DTO not found. Generating: " + fullClassName);
            DtoGeneratorService dtoGeneratorService = new DtoGeneratorServiceImpl();
            dtoGeneratorService.generateDto(simpleClassName, basePackage);
        }
    }

    private void writeFile(String templatePath, String fileName, String packagePath, Map<String, String> replacements) {
        String content = TemplateUtils.loadTemplate(templatePath, replacements);
        if (content == null) {
            ConsolePrinter.error("Template not found or failed to load: " + templatePath);
            return;
        }

        String dirPath = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packagePath.replace(".", "/");
        File outputFile = new File(dirPath, fileName);

        try {
            outputFile.getParentFile().mkdirs();
            if (outputFile.exists()) {
                ConsolePrinter.error("File already exists: " + outputFile.getPath());
                return;
            }

            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(content);
            }

            ConsolePrinter.println("Created: " + outputFile.getPath());
        } catch (IOException e) {
            ConsolePrinter.error("Error writing file: " + e.getMessage());
        }
    }
}
