package com.oranbyte.acolyte.utils;

public class ConsolePrinter {

    private static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    public static void print(String message){
        System.out.print(message);
    }
    public static void println(String message){
        System.out.println(message);
    }
    public static void print(String message, String color){
        System.out.print(color + message + ConsolePrinter.RESET);
    }
    public static void println(String message, String color){
        System.out.println(color + message + ConsolePrinter.RESET);
    }
    public static void error(String message){
        System.out.println(ConsolePrinter.RED + message + ConsolePrinter.RESET);
    }
    public static void danger(String message){
        System.out.println(ConsolePrinter.RED + message + ConsolePrinter.RESET);
    }
    public static void success(String message){
        System.out.println(ConsolePrinter.GREEN + message + ConsolePrinter.RESET);
    }
    public static void info(String message){
        System.out.println(ConsolePrinter.CYAN + message + ConsolePrinter.RESET);
    }
    public static void warning(String message){
        System.out.println(ConsolePrinter.YELLOW + message + ConsolePrinter.RESET);
    }
    public static void primary(String message){
        System.out.println(ConsolePrinter.BLUE + message + ConsolePrinter.RESET);
    }
    public static void secondary(String message){
        System.out.println(ConsolePrinter.PURPLE + message + ConsolePrinter.RESET);
    }


}
