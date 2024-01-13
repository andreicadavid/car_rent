package com.example.demo1;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {


    private static Settings instance;
    private final String repoType;
    private final String repoFileCar;
    private final String repoFileRent;

    private Settings(String repoType, String repoFileCar, String repoFileRent) {
        this.repoType = repoType;
        this.repoFileCar = repoFileCar;
        this.repoFileRent = repoFileRent;
    }

    public String getRepoFileCar() {
        return repoFileCar;
    }

    public String getRepoFileRent() {
        return repoFileRent;
    }

    public String getRepoType() {
        return repoType;
    }

    public static Properties loadSettings() {
        try (FileReader fr = new FileReader("C:\\MAP\\proiecte colegi\\a4-rebecabaciu\\a4-rebecabaciu\\src\\main\\java\\com\\example\\demo1\\settings.properties")) {
            Properties settings = new Properties();
            settings.load(fr);
            return settings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Settings getInstance() {
        Properties properties = loadSettings();
        instance = new Settings(properties.getProperty("repo_type"), properties.getProperty("repo_fileCar"), properties.getProperty("repo_fileRent"));
        return instance;
    }
}
