package com.ecrick.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIOUtil {
    public static final String PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/";
    public static final String ERROR_PATH = System.getProperty("user.dir") + "/src/main/resources/error/";

    public static void writeError(String fileName, String data) {
        String errorPath = ERROR_PATH + fileName;
        if (new File(errorPath).exists())
            errorPath = errorPath + "_" + System.currentTimeMillis() + fileName;
        writeFile(errorPath, data);
    }

    public static void write(String fileName, String data) {
        writeFile(PATH + fileName, data);
    }

    private static void writeFile(String path, String data) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(PATH + fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] getFileList() {
        return new File(FileIOUtil.PATH).list();
    }

    public static String[] getErrorFileList() {
        return new File(FileIOUtil.ERROR_PATH).list();
    }

}
