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

@CommandLine.Command(name = "make:service", description = "Create a new service with implementation class")
public class MakeServiceCommand implements Runnable{

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of Service Interface")
    private String serviceName;

    @Override
    public void run() {
        String interfaceName = CaseConverter.toPascalCase(serviceName.replaceAll("[^a-zA-Z0-9_-]", ""));
        if(interfaceName.endsWith("Service")){
            interfaceName += "Service";
        }

        String basePackage = root.getContext().getBasePackage();
        String servicePackage = basePackage + ".service";
        String packagePath = servicePackage.replace(".", "/");

        String serviceInterfaceTemplatePath = "templates/service.template";
        String serviceImplInterfaceTemplatePath = "templates/service-impl.template";

        Map<String, String> serviceTemplateReplacement = new HashMap<>();
        serviceTemplateReplacement.put("packageName", servicePackage);
        serviceTemplateReplacement.put("interfaceName", interfaceName);

        Map<String, String> serviceImplTemplateReplacement = new HashMap<>();
        serviceImplTemplateReplacement.put("packageName", servicePackage);
        serviceImplTemplateReplacement.put("interfaceName", interfaceName);

        String serviceContent = TemplateUtils.loadTemplate(serviceInterfaceTemplatePath, serviceTemplateReplacement);
        String serviceImplContent = TemplateUtils.loadTemplate(serviceImplInterfaceTemplatePath, serviceImplTemplateReplacement);

        if (serviceContent == null || serviceImplContent == null) {
            ConsolePrinter.error("Error loading templates or performing replacements.");
            return;
        }

        String baseDir = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packagePath;
        File serviceFile = new File(baseDir, interfaceName + ".java");

        String interfaceImplName = interfaceName + "Impl";

        File serviceImplFile = new File(baseDir + "/impl", interfaceImplName + ".java");

        try {
            serviceFile.getParentFile().mkdirs();
            serviceImplFile.getParentFile().mkdirs();

            boolean serviceCreated = serviceFile.createNewFile();
            boolean implCreated = serviceImplFile.createNewFile();

            if (serviceCreated) {
                try (FileWriter writer = new FileWriter(serviceFile)) {
                    writer.write(serviceContent);
                }
                ConsolePrinter.println("Service interface created at: " + serviceFile.getPath());
            } else {
                ConsolePrinter.error("Service interface already exists at: " + serviceFile.getPath());
            }

            if (implCreated) {
                try (FileWriter writer = new FileWriter(serviceImplFile)) {
                    writer.write(serviceImplContent);
                }
                ConsolePrinter.println("Service implementation created at: " + serviceImplFile.getPath());
            } else {
                ConsolePrinter.error("Service implementation already exists at: " + serviceImplFile.getPath());
            }

        } catch (IOException e) {
            ConsolePrinter.error("Error writing service files: " + e.getMessage());
        }

    }
}
