package com.oranbyte.acolyte;

import com.oranbyte.acolyte.services.ApplicationService;
import com.oranbyte.acolyte.services.impl.ApplicationServiceImpl;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
//       int exitCode = new CommandLine(new AcolyteCommandRoot()).execute(args);

//        testing...
//        System.out.println(CaseConverter.toPascalCase("sElse"));
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter command : ");
        String command = sc.nextLine();

        int exitCode = new CommandLine(new AcolyteCommandRoot()).execute(command.split(" "));
        System.exit(exitCode);

//      ------------------- BEGIN - STILL NOT READY WORKING ON IT -----------------------

//        File file = new File("C:\\Users\\Shubham\\Documents\\GitHub\\Html-CkEditor-spring-boot");
//        ApplicationService service = new ApplicationServiceImpl();
//
//        String tool = service.detectBuildTool(file);
//        if(tool == null){
//            ConsolePrinter.error("No recognizable build tool found (Maven or Gradle).");
//            return;
//        }
//        try{
//
//            service.runSpringBootProject(tool, file);
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//      ------------------- END - STILL NOT READY WORKING ON IT -----------------------
    }
}
