package ru.levelp.at.homework7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.levelp.at.homework7.context.TestContext;
import ru.levelp.at.homework7.listener.AllureAttachmentCallback;
import ru.levelp.at.homework7.steps.ActionStep;
import ru.levelp.at.homework7.steps.AssertionStep;

@ExtendWith(AllureAttachmentCallback.class)
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
        TestContext.getInstance().putObject("driver", driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        TestContext.clear();
    }
}
