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

    private static final String MAIL_RU_LOGIN = "v.khand@mail.ru";
    private static final String MAIL_RU_PASSWORD = "Very1secure1password";

    private static final String SUBJECT = "FirstExercise. Тема письма";
    private static final String MESSAGE_TEXT = "FirstExercise. Текст для заполнения тела письма.";
    private static final String LETTER_LIST_ITEM = "a.js-letter-list-item";
    private static final String RIGHT_MENU = "img.ph-avatar-img";
    private static final String LEFT_MENU_DRAFTS = "[href='/drafts/?']";
    private static final String LEFT_MENU_SENT = "[href='/sent/?']";
    private static final String PAGE_TITLE_DRAFT = "Черновики - Почта Mail.ru";
    private static final String PAGE_TITLE_SENT = "Отправленные - Почта Mail.ru";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @Test
    void saveAndSendDraftMessage() {

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

        // 3. Создать новое письмо (заполнить адресата, тему письма и тело)
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.compose-button")))
            .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .xpath("//div[@data-type='to']//input"))).sendKeys(MAIL_RU_LOGIN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='Subject']")))
            .sendKeys(SUBJECT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='textbox']")))
            .sendKeys(MESSAGE_TEXT);

        // 4. Сохранить его как черновик
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test-id='save']")))
            .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[title='Закрыть']"))).click();

        // 5. Verify, что письмо сохранено в черновиках
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_SENT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_SENT));
        final int sentMessagesBefore = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_DRAFTS))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
        final int draftMessagesBefore = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        wait.until(ExpectedConditions.elementToBeClickable(By
            .cssSelector(LETTER_LIST_ITEM + ":nth-of-type(1)"))).click();

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

        // 7. Отправить письмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='send']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button2_close"))).click();

        // 8. Verify, что письмо исчезло из черновиков
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector(LEFT_MENU_DRAFTS))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(LETTER_LIST_ITEM), draftMessagesBefore - 1));

        // 9. Verify, что письмо появилось в папке отправленные
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_SENT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_SENT));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(LETTER_LIST_ITEM), sentMessagesBefore + 1));

        // 10. Выйти из учётной записи
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(RIGHT_MENU))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("[data-testid='whiteline-account-exit']"))).click();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
