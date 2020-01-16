package com.upload.file.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public final class FileUtils {

    // user.home - directory with user file. In linux it's `/home/username`
    public static final Path SOAPBOX_HOME_FOLDER = Paths.get(System.getProperty("user.home"), ".soapbox");
    public static final Path ACCOUNTS_FOLDER = SOAPBOX_HOME_FOLDER.resolve("accounts");
    public static final Path DB_FOLDER = SOAPBOX_HOME_FOLDER.resolve("db");
    public static final Path VIDEO_FOLDER = SOAPBOX_HOME_FOLDER.resolve("video");
    public static final Path LOGS_FOLDER = SOAPBOX_HOME_FOLDER.resolve("logs");
    public static final Path TMP_FOLDER = SOAPBOX_HOME_FOLDER.resolve("tmp");

    static {
        Arrays.asList(SOAPBOX_HOME_FOLDER, ACCOUNTS_FOLDER, DB_FOLDER, VIDEO_FOLDER, LOGS_FOLDER, TMP_FOLDER).forEach(folder -> {
            File f = folder.toFile();
            if ((!f.exists() || !f.isDirectory()) && !f.mkdirs()) {
                throw new RuntimeException("Error creating folder(s)" + f.toString());
            }
        });
    }

    private FileUtils() {
    }

    public static String removeExtension(String path) {
        int lastIndexOf = path.lastIndexOf('.');
        return lastIndexOf != -1 ? path.substring(0, lastIndexOf) : path;
    }
}
