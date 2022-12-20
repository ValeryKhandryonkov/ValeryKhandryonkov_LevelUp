package ru.levelp.at.homework5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetUserCredentials {

    public String getCredentialByKey(String key) {

        FileInputStream fis;
        try {
            fis = new FileInputStream("src/test/resources/UserCredentials.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties userCredentials = new Properties();

        try {
            userCredentials.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return userCredentials.getProperty(key);
    }
}
