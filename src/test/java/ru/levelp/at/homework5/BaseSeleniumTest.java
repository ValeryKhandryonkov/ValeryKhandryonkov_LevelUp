package ru.levelp.at.homework5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.levelp.at.homework5.steps.ActionStep;
import ru.levelp.at.homework5.steps.AssertionStep;

public abstract class BaseSeleniumTest {

    protected WebDriver driver;

    protected ActionStep actionStep;
    protected AssertionStep assertionStep;

    private static final GetUserCredentials userCredentials = new GetUserCredentials();
    protected static final String MAIL_RU_LOGIN = userCredentials.getCredentialByKey("user1_login");
    protected static final String MAIL_RU_PASSWORD = userCredentials.getCredentialByKey("user1_password");

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        actionStep = new ActionStep(driver);
        assertionStep = new AssertionStep(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
