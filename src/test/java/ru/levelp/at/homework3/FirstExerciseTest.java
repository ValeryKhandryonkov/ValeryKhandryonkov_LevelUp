package ru.levelp.at.homework3;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
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

public class FirstExerciseTest {

    private WebDriver driver;
    private WebDriverWait wait;

    protected static final String MAIL_RU_LOGIN = "v.khand@mail.ru";
    protected static final String MAIL_RU_PASSWORD = "Very1secure1password";

    private static final String SUBJECT = "FirstExercise - Тест";
    private static final String MESSAGE_TEXT = "FirstExercise. Текст для заполнения тела письма.";
    private static final String LETTER_LIST_ITEM = "a.js-letter-list-item";
    private static final String LEFT_MENU_DRAFTS = "[href='/drafts/?']";
    private static final String LEFT_MENU_SENT = "[href='/sent/?']";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    // Войти в почту
    // Assert, что вход выполнен успешно
    @Test
    void openMailHomePageAndAuthorize() {
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

        String actualUserLogin = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.cssSelector("img.ph-avatar-img"))).getAttribute("alt");

        assertThat(actualUserLogin).isEqualTo(MAIL_RU_LOGIN);

        // Создать новое письмо (заполнить адресата, тему письма и тело)
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.compose-button")))
            .sendKeys("n");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .xpath("//div[@data-type='to']//input"))).sendKeys(MAIL_RU_LOGIN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='Subject']")))
            .sendKeys(SUBJECT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='textbox']")))
            .sendKeys(MESSAGE_TEXT);

        // Сохранить его как черновик
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='save']")))
            .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='Закрыть']"))).click();

        // Verify, что письмо сохранено в черновиках
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_SENT))).click();
        wait.until(ExpectedConditions.titleIs("Отправленные - Почта Mail.ru"));
        int numberOfSentMessagesBeforeSendingDraft = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();
        System.out.println("Number of sent messages on the page before sending draft: "
            + numberOfSentMessagesBeforeSendingDraft);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector(LEFT_MENU_DRAFTS))).click();
        wait.until(ExpectedConditions.titleIs("Черновики - Почта Mail.ru"));

        int numberOfDraftsBeforeSendingDraft = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();
        System.out.println("Number of drafts on the page before sending draft: " + numberOfDraftsBeforeSendingDraft);

        wait.until(ExpectedConditions.elementToBeClickable(By
            .cssSelector(LETTER_LIST_ITEM + ":nth-of-type(1)"))).click();

        // Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        var actualAddressee = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .xpath("//div[@data-type='to']//span[contains(@class, 'text')]"))).getText();
        assertThat(actualAddressee).isEqualTo(MAIL_RU_LOGIN);

        var actualSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .xpath("//div[contains(@class, 'compose-app_window')]//input[@name='Subject']")))
            .getAttribute("value");
        assertThat(actualSubject).isEqualTo(SUBJECT);

        var actualMessageText = wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("div.js-readmsg-msg > div > div > div > :first-child"))).getText();
        assertThat(actualMessageText).isEqualTo(MESSAGE_TEXT);

        // Отправить письмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='send']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button2_close"))).click();

        // Verify, что письмо исчезло из черновиков
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector(LEFT_MENU_DRAFTS))).click();
        wait.until(ExpectedConditions.titleIs("Черновики - Почта Mail.ru"));
        int numberOfDraftsAfterSendingDraft = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();
        System.out.println("Number of drafts on the page after sending draft: " + numberOfDraftsAfterSendingDraft);
        assertThat(numberOfDraftsAfterSendingDraft).isEqualTo(numberOfDraftsBeforeSendingDraft - 1);

        // Verify, что письмо появилось в папке отправленные
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_SENT))).click();
        wait.until(ExpectedConditions.titleIs("Отправленные - Почта Mail.ru"));
        int numberOfSentMessagesAfterSendingDraft = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();
        System.out.println("Number of sent messages on the page after sending draft: "
            + numberOfSentMessagesAfterSendingDraft);
        assertThat(numberOfSentMessagesAfterSendingDraft).isEqualTo(numberOfSentMessagesBeforeSendingDraft + 1);

        // Выйти из учётной записи
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.ph-avatar-img"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("[data-testid='whiteline-account-exit']"))).click();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
