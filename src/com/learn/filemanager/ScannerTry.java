package com.learn.filemanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * Created by Vitalik on 14.09.2016.
 */
public class ScannerTry {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Input path and name of file that you want to rename like(./Poem/Poem.txt)");
        String pathOfFile = scanner.next();
        System.out.println("Input new name of file like(Poem.txt)");
        String newFileName = scanner.next();
//        String fileToFind = File.separator + pathOfFileToFind;

        Path f = Paths.get(pathOfFile);
        Path rF = Paths.get(f.getParent() + "/" + newFileName);
        System.out.println(rF);

//        try {
//            Files.move(f, rF, StandardCopyOption.REPLACE_EXISTING);
//            System.out.println("File was successfully renamed");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error: Unable to rename file");
//        }
//        String input = "1 fish 2 fish red fish blue fish";
//        String input = "1\\2\\";
//        Scanner s = new Scanner(input).useDelimiter("\\s*\\\\s*");
//        System.out.println(s.nextInt());
//        System.out.println(s.nextInt());
//        System.out.println(s.next());
//        System.out.println(s.next());
//        s.close();
    }
}
