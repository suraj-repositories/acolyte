package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.ViewGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewGeneratorServiceImpl implements ViewGeneratorService {

    @Override
    public void generateView(String viewName) {
        String viewFileName = CaseConverter.toViewName(viewName);

        String templatePath = "templates/view.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("viewFileName", viewFileName);

        String content = TemplateUtils.loadTemplate(templatePath, replacements);

        if (content != null) {
            String filePath = AppConstants.PROJECT_DIRECTORY + "/src/main/resources/templates/" + viewFileName + ".html";
            File file = new File(filePath);

            try {
                file.getParentFile().mkdirs();
                if (file.exists()) {
                    ConsolePrinter.error("File already exists: " + filePath);
                } else {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(content);
                    }
                    ConsolePrinter.println("View created at: " + filePath);
                }
            } catch (IOException e) {
                ConsolePrinter.error("Error creating view: " + e.getMessage());
            }
        } else {
            ConsolePrinter.error("Error loading template or performing replacements.");
        }
    }
}
