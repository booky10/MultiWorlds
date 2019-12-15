package tk.t11e.util;
// Created by booky10 in MultiWorlds (15:23 15.12.19)

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

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