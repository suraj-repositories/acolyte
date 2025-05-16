package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.ServiceGenerationService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServiceGenerationServiceImpl implements ServiceGenerationService {

    @Override
    public void generateServiceInterface(String serviceName, String basePackage) {
        String interfaceName = formatInterfaceName(serviceName);
        String servicePackage = basePackage + ".service";
        String packagePath = servicePackage.replace(".", "/");

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", servicePackage);
        replacements.put("interfaceName", interfaceName);

        String serviceContent = TemplateUtils.loadTemplate("templates/service.template", replacements);

        if (serviceContent == null) {
            ConsolePrinter.error("Error loading service interface template.");
            return;
        }

        File serviceFile = new File(AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packagePath, interfaceName + ".java");

        try {
            serviceFile.getParentFile().mkdirs();
            if (serviceFile.createNewFile()) {
                try (FileWriter writer = new FileWriter(serviceFile)) {
                    writer.write(serviceContent);
                }
                ConsolePrinter.println("Service interface created at: " + serviceFile.getPath());
            } else {
                ConsolePrinter.error("Service interface already exists at: " + serviceFile.getPath());
            }
        } catch (IOException e) {
            ConsolePrinter.error("Error writing service interface: " + e.getMessage());
        }
    }

    @Override
    public void generateServiceImplementation(String serviceName, String basePackage) {
        String interfaceName = formatInterfaceName(serviceName);
        String servicePackage = basePackage + ".service";
        String implPackage = servicePackage + ".impl";
        String implPath = implPackage.replace(".", "/");
        String className = interfaceName + "Impl";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", servicePackage);
        replacements.put("interfaceName", interfaceName);

        String implContent = TemplateUtils.loadTemplate("templates/service-impl.template", replacements);

        if (implContent == null) {
            ConsolePrinter.error("Error loading service implementation template.");
            return;
        }

        File implFile = new File(AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + implPath, className + ".java");

        try {
            implFile.getParentFile().mkdirs();
            if (implFile.createNewFile()) {
                try (FileWriter writer = new FileWriter(implFile)) {
                    writer.write(implContent);
                }
                ConsolePrinter.println("Service implementation created at: " + implFile.getPath());
            } else {
                ConsolePrinter.error("Service implementation already exists at: " + implFile.getPath());
            }
        } catch (IOException e) {
            ConsolePrinter.error("Error writing service implementation: " + e.getMessage());
        }
    }

    private String formatInterfaceName(String rawName) {
        String name = CaseConverter.toPascalCase(rawName.replaceAll("[^a-zA-Z0-9_-]", ""));
        return CaseConverter.appendIfNotAvailable(name, "Service");
    }
}
