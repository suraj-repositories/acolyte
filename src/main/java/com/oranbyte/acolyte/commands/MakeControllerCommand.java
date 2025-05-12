package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@CommandLine.Command(name = "make:controller", description = "Create a new simple controller")
public class MakeControllerCommand implements Runnable {

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the controller")
    private String controllerName;

    @CommandLine.Option(
            names = "--resource",
            arity = "0..*",
            description = "Specify resource functions to generate (e.g. index, show, create, store, edit, update, destroy)"
    )
    private List<String> resourceFunctions;

    @Override
    public void run() {
        String className = controllerName.replaceAll("[^a-zA-Z0-9]", "");
        if (!className.endsWith("Controller")) {
            className += "Controller";
        }

        String basePackage = root.getContext().getBasePackage();
        String controllerPackage = basePackage + ".controller";
        String packagePath = controllerPackage.replace('.', '/');

        String templatePath = "templates/controller.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", controllerPackage);
        replacements.put("endpoint", className.toLowerCase().replace("controller", ""));
        replacements.put("className", className);

        if (resourceFunctions != null && !resourceFunctions.isEmpty()) {
            ConsolePrinter.info("Generating resource functions: " + String.join(", ", resourceFunctions));

            Set<String> functions = new HashSet<>();
            for (String func : resourceFunctions) {
                functions.add(func.toLowerCase());
            }

            StringBuilder methodBuilder = new StringBuilder();

            for (String function : functions) {
                switch (function) {
                    case "index" -> methodBuilder.append(generateIndexFunction());
                    case "create" -> methodBuilder.append(generateCreateFunction());
                    case "store" -> methodBuilder.append(generateStoreFunction());
                    case "show" -> methodBuilder.append(generateShowFunction());
                    case "edit" -> methodBuilder.append(generateEditFunction());
                    case "update" -> methodBuilder.append(generateUpdateFunction());
                    case "destroy" -> methodBuilder.append(generateDestroyFunction());
                }
            }

            replacements.put("resourceFunctions", TemplateUtils.indent(methodBuilder.toString().trim(), 4));
        }else if(resourceFunctions != null && resourceFunctions.isEmpty()){
            StringBuilder methodBuilder = new StringBuilder();
            methodBuilder.append(generateIndexFunction())
                    .append(generateCreateFunction())
                    .append(generateStoreFunction())
                    .append(generateShowFunction())
                    .append(generateEditFunction())
                    .append(generateUpdateFunction())
                    .append(generateDestroyFunction());
            replacements.put("resourceFunctions", TemplateUtils.indent(methodBuilder.toString().trim(), 4));
        }else {
            ConsolePrinter.info("Generating simple controller (no resource functions).");
            replacements.put("resourceFunctions", "");
        }

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
                    ConsolePrinter.println("Controller created at: " + filePath);
                }
            } catch (IOException e) {
                ConsolePrinter.error("Error creating controller: " + e.getMessage());
            }
        } else {
            ConsolePrinter.error("Error loading template or performing replacements.");
        }
    }

    private String generateIndexFunction() {
        StringBuilder sb = new StringBuilder();
        sb.append("@GetMapping\n");
        sb.append("public String index() {\n");
        sb.append("    return \"index\";\n");
        sb.append("}\n\n");
        return sb.toString();
    }

    private String generateCreateFunction() {
        StringBuilder sb = new StringBuilder();
        sb.append("@GetMapping(\"/create\")\n");
        sb.append("public String create() {\n");
        sb.append("    return \"create\";\n");
        sb.append("}\n\n");
        return sb.toString();
    }

    private String generateStoreFunction() {
        StringBuilder sb = new StringBuilder();
        sb.append("@PostMapping\n");
        sb.append("public String store() {\n");
        sb.append("    return \"store\";\n");
        sb.append("}\n\n");
        return sb.toString();
    }

    private String generateShowFunction() {
        StringBuilder sb = new StringBuilder();
        sb.append("@GetMapping(\"/{id}\")\n");
        sb.append("public String show(@PathVariable Long id) {\n");
        sb.append("    return \"show\";\n");
        sb.append("}\n\n");
        return sb.toString();
    }

    private String generateEditFunction() {
        StringBuilder sb = new StringBuilder();
        sb.append("@GetMapping(\"/{id}/edit\")\n");
        sb.append("public String edit(@PathVariable Long id) {\n");
        sb.append("    return \"edit\";\n");
        sb.append("}\n\n");
        return sb.toString();
    }

    private String generateUpdateFunction() {
        StringBuilder sb = new StringBuilder();
        sb.append("@PutMapping(\"/{id}\")\n");
        sb.append("public String update(@PathVariable Long id) {\n");
        sb.append("    return \"update\";\n");
        sb.append("}\n\n");
        return sb.toString();
    }

    private String generateDestroyFunction() {
        StringBuilder sb = new StringBuilder();
        sb.append("@DeleteMapping(\"/{id}\")\n");
        sb.append("public String destroy(@PathVariable Long id) {\n");
        sb.append("    return \"destroy\";\n");
        sb.append("}\n\n");
        return sb.toString();
    }

}
