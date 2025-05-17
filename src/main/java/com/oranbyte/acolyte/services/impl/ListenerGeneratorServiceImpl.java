package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.services.ListenerGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.util.HashMap;
import java.util.Map;

public class ListenerGeneratorServiceImpl implements ListenerGeneratorService {

    private static final String LISTENER_TEMPLATE = "templates/listener.template";

    @Override
    public void generateListener(String listenerName, String basePackage) {
        String className = CaseConverter.appendIfNotAvailable(CaseConverter.toClassName(listenerName), "Listener");

        String listenerPackage = basePackage + ".listener";
        String packagePath = listenerPackage.replace(".", "/");

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", listenerPackage);
        replacements.put("className", className);

        String fileName = className + ".java";
        TemplateUtils.createSourceFileFromTemplate(LISTENER_TEMPLATE, fileName, packagePath,replacements);


    }

}
