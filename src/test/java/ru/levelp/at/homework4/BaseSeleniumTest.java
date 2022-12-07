package ru.levelp.at.homework4;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseSeleniumTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final GetUserCredentials userCredentials = new GetUserCredentials();
    protected static final String MAIL_RU_LOGIN = userCredentials.getCredentialByKey("user1_login");
    protected static final String MAIL_RU_PASSWORD = userCredentials.getCredentialByKey("user1_password");

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
