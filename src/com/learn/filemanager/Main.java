package com.learn.filemanager;

import java.io.IOException;
import java.util.Scanner;

/**
 * The class creates a menu for managing a file manager application.
 *
 * @author Vitalii Prykhid
 */

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        while (true) {
            fileManager.menu();
            String key = scanner.next();
            try {
                switch (key) {
                    case "1": {
                        fileManager.createFile();
                        break;
                    }
                    case "2": {
                        fileManager.createDirectory();
                        break;
                    }
                    case "3": {
                        fileManager.convertTextFileToPdf();
                        break;
                    }
                    case "4": {
                        fileManager.copyFilesFromInDirectoryToOtherDirectory();
                        break;
                    }
                    case "5": {
                        fileManager.deleteFile();
                        break;
                    }
                    case "6": {
                        System.out.println("If you want to delete empty directory press 1, press 2 to delete directory with content");
                        int choice = scanner.nextInt();
                        if (choice == 1) {
                            fileManager.deleteDirectory();
                        } else if (choice == 2) {
                            fileManager.deleteDirectoryWithContent();
                        }
                        break;
                    }
                    case "7": {
                        fileManager.renameFile();
                        break;
                    }
                    case "8": {
                        fileManager.renameDirectory();
                        break;
                    }
                    case "9": {
                        fileManager.showAllFromDirectory();
                        break;
                    }
                    case "10": {
                        fileManager.showFileFromDirectory();
                        break;
                    }
                    case "11": {
                        fileManager.convertFromPdfFileToText();
                        break;
                    }
                    case "12":
                        return;
                    default:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Exception" + e);
            }
        }
    }
}
