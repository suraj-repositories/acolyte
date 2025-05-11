package com.oranbyte.acolyte.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TemplateUtils {

    public static String loadTemplate(String templatePath, Map<String, String> replacements) {
        // Load the template from the classpath (use relative path)
        try (InputStream inputStream = TemplateUtils.class.getClassLoader().getResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new IOException("Template file not found in the classpath: " + templatePath);
            }

            // Read the content of the template
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Replace placeholders in the template
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                content = content.replace("${" + entry.getKey() + "}", entry.getValue());
            }

            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
