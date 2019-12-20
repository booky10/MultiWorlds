package tk.t11e.util;
// Created by booky10 in MultiWorlds (15:23 15.12.19)

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ZipUtil {

    public static void unzipArchive(String input, File outputFolder) throws IOException {
        if (!outputFolder.exists()) outputFolder.mkdir();

        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(input));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            Path filePath = Paths.get(outputFolder.getAbsolutePath(), zipEntry.getName());
            if (!zipEntry.isDirectory())
                unzipFiles(zipInputStream, filePath);
            else
                Files.createDirectories(filePath);
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    private static void unzipFiles(ZipInputStream zipInputStream, Path unzipFilePath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(unzipFilePath.toAbsolutePath().toString());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        byte[] bytes = new byte[1024];
        int i;
        while ((i = zipInputStream.read(bytes)) != -1)
            bufferedOutputStream.write(bytes, 0, i);
    }

    public static boolean zipFolder(File input, File output) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(output);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

        boolean finished = zipFilePrivate(input, input.getName(), zipOutputStream);

        zipOutputStream.close();
        fileOutputStream.close();

        return finished;
    }

    private static boolean zipFilePrivate(File input, String inputFileName, ZipOutputStream output)
            throws IOException {
        if (input.isHidden()) return false;

        if (input.isDirectory()) {
            if (inputFileName.endsWith("/"))
                output.putNextEntry(new ZipEntry(inputFileName));
            else
                output.putNextEntry(new ZipEntry(inputFileName + "/"));
            output.closeEntry();
            File[] children = input.listFiles();
            if (children == null) return false;
            for (File childFile : children)
                zipFilePrivate(childFile, inputFileName + "/" + childFile.getName(), output);
        } else {
            FileInputStream fileInputStream = new FileInputStream(input);
            output.putNextEntry(new ZipEntry(inputFileName));
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fileInputStream.read(bytes)) >= 0)
                output.write(bytes, 0, length);
        }
        return true;
    }
}