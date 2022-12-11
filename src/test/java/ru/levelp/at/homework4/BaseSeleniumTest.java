package ru.levelp.at.homework4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class BaseSeleniumTest {

    protected WebDriver driver;

    private static final GetUserCredentials userCredentials = new GetUserCredentials();
    protected static final String MAIL_RU_LOGIN = userCredentials.getCredentialByKey("user1_login");
    protected static final String MAIL_RU_PASSWORD = userCredentials.getCredentialByKey("user1_password");

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
