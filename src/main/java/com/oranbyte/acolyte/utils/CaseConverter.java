package com.oranbyte.acolyte.utils;

public class CaseConverter {

    public static String toPascalCase(String input) {
      if (input == null || input.isEmpty()) return input;

      String normalized = input.replaceAll("[-_]", " ");
      normalized = normalized.replaceAll("([a-z])([A-Z])", "$1 $2");

      StringBuilder pascalCase = new StringBuilder();
      for (String word : normalized.split("\\s+")) {
        if (!word.isEmpty()) {
          pascalCase.append(Character.toUpperCase(word.charAt(0)));
          if (word.length() > 1) {
            pascalCase.append(word.substring(1).toLowerCase());
          }
        }
      }

      return pascalCase.toString();
    }



}
