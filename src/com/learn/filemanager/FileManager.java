package com.learn.filemanager;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * The class is created for storing and handle of information about the file manager application.
 */
public class FileManager {
    //    Path path = FileSystems.getDefault().getPath("./Poem.txt");
    private static final Scanner scanner = new Scanner(System.in);
    public static final int PAGE_NUMBER = 1;
    public static final int SIZE = 11;
    public static final int SPACING = 50;

    /**
     * The method allows to create the menu.
     */
    public void menu() {
        System.out.println("1) Create File.\n2) Create Directory.\n3) Convert textFile to Pdf\n4) Copy Files from one Directory to other Directory.\n5) Delete File.\n6) Delete Directory.\n7) Rename File.\n8) Rename Directory.\n9) Show all from Directory.\n10) Show File from Directory.\n11) Convert from Pdf file to text.\n12) Exit.");
    }

    /**
     * The method allows to create a file choosing a path.
     */
    public void createFile() {
        System.out.println("Input path and name of file that you want to create like(./Poem.txt)");
        Path path = getPath();
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method allows to get and save an information about path.
     *
     * @return path to file.
     */
    private Path getPath() {
        String pathOfFile = scanner.next();
        return Paths.get(pathOfFile);
    }

    /**
     * The method allows to create a directory choosing a path.
     */
    public void createDirectory() {
        System.out.println("Input path and name of directory that you want to create like(./Poem)");
        Path path = getPath();
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method allows to convert a file from pdf format to text format.
     */
    public void convertFromPdfFileToText() {
        System.out.println("Input path and name of a file that you want to convert like(./Poem/Poem.pdf)");
        Path path = getPath();
        System.out.println("Input new name of file like(PoemNew)");
        String fileName = scanner.next();
        String newNameOfDirectory = path.getParent() + "/" + fileName;
        try {
            Document document = new Document();
            document.open();
            PdfReader reader = new PdfReader(String.valueOf(path));
            PdfDictionary dictionary = reader.getPageN(PAGE_NUMBER);
            PRIndirectReference reference = (PRIndirectReference)
                    dictionary.get(PdfName.CONTENTS);
            PRStream stream = (PRStream) PdfReader.getPdfObject(reference);
            byte[] bytes = PdfReader.getStreamBytes(stream);
            PRTokeniser tokenizer = new PRTokeniser(bytes);
            FileOutputStream fos = new FileOutputStream(newNameOfDirectory);
            StringBuffer buffer = new StringBuffer();
            while (tokenizer.nextToken()) {
                if (tokenizer.getTokenType() == tokenizer.getTokenType()) {
                    buffer.append(tokenizer.getStringValue());
                    buffer.append("\n");
                }
            }
            String test = buffer.toString();
            StringReader stReader = new StringReader(test);
            int t;
            while ((t = stReader.read()) > 0)
                fos.write(t);
            document.add(new Paragraph(".."));
            document.close();

        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
    }

    /**
     * The method allows to convert a file from text format to pdf format.
     */
    public void convertTextFileToPdf() {
        System.out.println("Input path and name of a file that you want to convert like(./Poem/Poem.txt)");
        Path path = getPath();
        System.out.println("Input new name of file like(PoemNew.pdf)");
        String fileName = scanner.next();
        String newNameOfDirectory = path.getParent() + "/" + fileName;
        File file = new File(String.valueOf(path));
        try {
            Document pdfDocument = new Document(PageSize.A4);
            System.out.println("!!!Writing to: " + file.getName().replace("txt", "pdf"));
            PdfWriter.getInstance(pdfDocument, new FileOutputStream(newNameOfDirectory)).setPdfVersion(PdfWriter.VERSION_1_7);
            pdfDocument.open();
            Anchor anchorTarget = new Anchor("First page of the document.");
            anchorTarget.setName("BackToTop");
            Paragraph paragraph1 = new Paragraph();

            paragraph1.setSpacingBefore(SPACING);

            paragraph1.add(anchorTarget);
            pdfDocument.add(paragraph1);

            pdfDocument.add(new Paragraph("Some more text on the first page with different color and font type.",
                    FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 255, 0, 0))));
            Font myfont = new Font();
            myfont.setStyle(Font.NORMAL);
            myfont.setSize(SIZE);
            pdfDocument.add(new Paragraph("\n"));
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        Paragraph para = new Paragraph(strLine + "\n", myfont);
                        para.setAlignment(Element.ALIGN_JUSTIFIED);
                        pdfDocument.add(para);
                    }
                }
                pdfDocument.close();
            } else {
                System.out.println("no such file exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method allows to copy the file from one path to onother.
     */
    public void copyFilesFromInDirectoryToOtherDirectory() {
        System.out.println("Input path and name of file that you want to copy FROM like(./Poem.txt)");
        String pathOfFileFrom = scanner.next();
        System.out.println("Input path and name of file that you want to copy TO like(./Poem/Poem.txt)");
        String pathOfFileTo = scanner.next();
        Path pathSource = Paths.get(pathOfFileFrom);
        Path pathDestination = Paths.get(pathOfFileTo);
        try {
            Files.copy(pathSource, pathDestination, REPLACE_EXISTING);
            System.out.println("Source file copied successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * The method allows to delete a file.
     */
    public void deleteFile() {
        System.out.println("Input path and name of file that you want to delete like(./Poem/Poem.txt)");
        Path path = getPath();
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * The method allows to delete a directory with content.
     */
    public void deleteDirectoryWithContent() {
        System.out.println("Input path and name of Directory that you want to delete like(./Poem)");
        Path path = getPath();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return delete(dir);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    return delete(file);
                }

                private FileVisitResult delete(Path pathToFile) throws IOException {
                    Files.delete(pathToFile);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Deleted dir :" + path);
    }

    /**
     * The method allows to delete an empty directory.
     */
    public void deleteDirectory() {
        System.out.println("Input path and name of Directory that you want to delete like(./Poem)");
        Path path = getPath();
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * The method allows to rename a file.
     */
    public void renameFile() {
        System.out.println("Input path and name of file that you want to rename like(./Poem/Poem.txt)");
        Path path = getPath();
        System.out.println("Input new name of file like(PoemNew.txt)");
        String fileName = scanner.next();
        String newNameOfDirectory = path.getParent() + "/" + fileName;
        Path renameFile = Paths.get(newNameOfDirectory);
        try {
            Files.move(path, renameFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File was successfully renamed");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to rename file");
        }
    }

    /**
     * The method allows to rename a directory.
     */
    public void renameDirectory() {
        System.out.println("Input path and name of Directory that you want to rename like(./Poem)");
        Path path = getPath();
        System.out.println("Input new name of Directory like(PoemNew)");
        String fileName = scanner.next();
        String newNameOfDirectory = path.getParent() + "/" + fileName;
        try {
            Files.move(path, path.resolveSibling(newNameOfDirectory));
            System.out.println("Directory was successfully renamed.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to rename directory.");
        }
//        File oldfile =new File("./Poem");//Java 1,7
//        File newfile =new File("./PoemDDDDDDDD");
//
//        if(oldfile.renameTo(newfile)){
//            System.out.println("File renamed");
//        }else{
//            System.out.println("Sorry! the file can't be renamed");
//        }
    }

    /**
     * The method allows to show the file from directory.
     */
    public void showFileFromDirectory() {
        System.out.println("Input path and name of Directory that you want like(./Poem)");
        Path path = getPath();
        System.out.println("Input name of file like(Poem.txt)");
        String pathOfFileToFind = scanner.next();
        String fileToFind = File.separator + pathOfFileToFind;
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();
                    if (fileString.endsWith(fileToFind)) {
                        System.out.println("File is found: " + file.getFileName());
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method allows to show all files from directory.
     */
    public void showAllFromDirectory() {
        System.out.println("Input path and name of Directory that you want to rename like(./Poem)");
        Path path = getPath();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes fileAttributes) {
                    System.out.println("Matching file : " + file.getFileName());
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
