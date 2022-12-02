package ru.levelp.at.homework3;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SecondExerciseTest {
    private WebDriver driver;
    private WebDriverWait wait;

    protected static final String MAIL_RU_LOGIN = "v.khand@mail.ru";
    protected static final String MAIL_RU_PASSWORD = "Very1secure1password";

    private static final String SUBJECT = "SecondExercise. Тема письма - Тест";
    private static final String MESSAGE_TEXT = "SecondExercise. Текст для заполнения тела письма.";
    private static final String LETTER_LIST_ITEM = "a.js-letter-list-item";
    private static final String RIGHT_MENU = "img.ph-avatar-img";
    private static final String LEFT_MENU_SENT = "[href='/sent/?']";
    private static final String LEFT_MENU_TEST = "[href='/1/?']";
    private static final String PAGE_TITLE_SENT = "Отправленные - Почта Mail.ru";
    private static final String PAGE_TITLE_TEST = "Тест - Почта Mail.ru";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @Test
    void sendNewMessageForTestFolder() {

        // 1. Войти в почту
        driver.get("https://mail.ru/");
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.cssSelector("[data-testid='enter-mail-primary']"))).click();
        WebElement frame = driver.findElement(By.cssSelector("iframe[class='ag-popup__frame__layout__iframe']"));
        driver.switchTo().frame(frame);

        var emailTextBox = wait
            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
        emailTextBox.sendKeys(MAIL_RU_LOGIN);
        emailTextBox.sendKeys(Keys.ENTER);

        var passwordTextBox = wait
            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
        passwordTextBox.sendKeys(MAIL_RU_PASSWORD);
        passwordTextBox.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ph-project-promo-close-icon")))
            .click();

        // 2. Assert, что вход выполнен успешно
        String actualUserLogin = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.cssSelector(RIGHT_MENU))).getAttribute("alt");
        assertThat(actualUserLogin).isEqualTo(MAIL_RU_LOGIN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_SENT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_SENT));
        final int sentMessagesBefore = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_TEST))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_TEST));
        final int testMessagesBefore = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();

        // 3. Создать новое письмо (заполнить адресата, тему письма и тело)
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.compose-button")))
            .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .xpath("//div[@data-type='to']//input"))).sendKeys(MAIL_RU_LOGIN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='Subject']")))
            .sendKeys(SUBJECT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='textbox']")))
            .sendKeys(MESSAGE_TEXT);

        // 4. Отправить письмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='send']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button2_close"))).click();

        // 5. Verify, что письмо появилось в папке отправленные
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_SENT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_SENT));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(LETTER_LIST_ITEM), sentMessagesBefore + 1));

        // 6. Verify, что письмо появилось в папке «Тест»
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_TEST))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_TEST));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(LETTER_LIST_ITEM), testMessagesBefore + 1));

        // 7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        wait.until(ExpectedConditions.elementToBeClickable(By
            .cssSelector(LETTER_LIST_ITEM + ":nth-of-type(1)"))).click();

        var actualAddressee = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector(".letter__recipients > .letter-contact"))).getAttribute("title");
        Assertions.assertThat(actualAddressee).isEqualTo(MAIL_RU_LOGIN);

        var actualSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                                    .cssSelector("h2.thread-subject"))).getText();
        Assertions.assertThat(actualSubject).isEqualTo(SUBJECT);

        var actualMessageText = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div.js-readmsg-msg > div > div > div > :first-child"))).getText();
        Assertions.assertThat(actualMessageText).isEqualTo(MESSAGE_TEXT);

        // 8. Выйти из учётной записи
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(RIGHT_MENU))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("[data-testid='whiteline-account-exit']"))).click();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
