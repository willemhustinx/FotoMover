package nl.willemhustinx.fotomover;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static String[] validExtensionsTemp = {".jpg", ".JPG", ".MP4", ".mp4"};
    public static List<String> validExtensions = Arrays.asList(validExtensionsTemp);

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(ANSI_RED + "    ______      __           __  ___                    " + ANSI_RESET);
        System.out.println(ANSI_RED + "   / ____/___  / /_____     /  |/  /___ _   _____  _____" + ANSI_RESET);
        System.out.println(ANSI_RED + "  / /_  / __ \\/ __/ __ \\   / /|_/ / __ \\ | / / _ \\/ ___/" + ANSI_RESET);
        System.out.println(ANSI_RED + " / __/ / /_/ / /_/ /_/ /  / /  / / /_/ / |/ /  __/ /    " + ANSI_RESET);
        System.out.println(ANSI_RED + "/_/    \\____/\\__/\\____/  /_/  /_/\\____/|___/\\___/_/     " + ANSI_RESET);
        System.out.println("                         by: Willem Hustinx    v0.1     ");
        System.out.println();

        File fromDirectory;
        File toDirectory;

        boolean pathFromFlag = false;
        do {
            System.out.print("Select from directory (full path): ");
            String fromPath = scanner.nextLine();
            fromDirectory = new File(fromPath);

            if (fromDirectory.exists() && fromDirectory.isDirectory()) {
                pathFromFlag = true;
            } else {
                System.out.println("Not a valid directory");
            }
        }
        while (!pathFromFlag);

        FileFinder f = new FileFinder(fromDirectory);
        f.findFiles();

        System.out.println();
        f.printExtensions();

        boolean pathToFlag = false;
        do {
            System.out.print("Select to directory (full path): ");
            String toPath = scanner.nextLine();
            toDirectory = new File(toPath);

            if (toDirectory.exists() && toDirectory.isDirectory()) {
                pathToFlag = true;
            } else {
                System.out.println("Not a valid directory");
            }
        }
        while (!pathToFlag);

        System.out.println("Move " + f.getFilesToMove() + " to " + toDirectory.getAbsolutePath());

        System.out.print("Confirm: ");

        String confirmation = scanner.next();
        confirmation = confirmation.toLowerCase();

        if(confirmation.equals("yes") || confirmation.equals("y")){
            System.out.print("copy");


            FileMover m = new FileMover(f.getFiles(), toDirectory);
            m.move();
        }



    }
}


