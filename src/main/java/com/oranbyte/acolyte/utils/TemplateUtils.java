package com.oranbyte.acolyte.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateUtils {

    public static String loadTemplate(String templatePath, Map<String, String> replacements) {
        try (InputStream inputStream = TemplateUtils.class.getClassLoader().getResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Template file not found in the classpath: " + templatePath);
            }

            String template = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));

            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            return template;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String indent(String code, int spaces) {
        String indent = " ".repeat(spaces);
        return Arrays.stream(code.split("\n"))
                .map(line -> indent + line)
                .collect(Collectors.joining("\n"));
    }

}
