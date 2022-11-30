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


public class ThirdExerciseTest {
    private WebDriver driver;
    private WebDriverWait wait;

    protected static final String MAIL_RU_LOGIN = "v.khand@mail.ru";
    protected static final String MAIL_RU_PASSWORD = "Very1secure1password";

    private static final String SUBJECT = "ThirdExercise. Тема письма";
    private static final String MESSAGE_TEXT = "ThirdExercise. Текст для заполнения тела письма.";
    private static final String LETTER_LIST_ITEM = "a.js-letter-list-item";
    private static final String LEFT_MENU_INBOX = "[href='/inbox/?']";
    private static final String LEFT_MENU_TRASH = "[href='/trash/?']";
    private static final String PAGE_TITLE_INBOX = "Входящие - Почта Mail.ru";
    private static final String PAGE_TITLE_TRASH = "Корзина - Почта Mail.ru";


    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    }

    @Test
    void sendNewMessageForInboxFolderAndDeleteMessage() {

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

        // 2. Assert, что вход выполнен успешно
        String actualUserLogin = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.cssSelector("img.ph-avatar-img"))).getAttribute("alt");
        assertThat(actualUserLogin).isEqualTo(MAIL_RU_LOGIN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_INBOX))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_INBOX));
        int receivedMessagesBefore = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();
        System.out.println("Received messages before: " + receivedMessagesBefore);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_TRASH))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_TRASH));
        int deletedMessagesBefore = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();
        System.out.println("Deleted messages before: " + deletedMessagesBefore);

        // 3. Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.compose-button")))
            .sendKeys("n");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .xpath("//div[@data-type='to']//input"))).sendKeys(MAIL_RU_LOGIN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='Subject']")))
            .sendKeys(SUBJECT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='textbox']")))
            .sendKeys(MESSAGE_TEXT);

        // 4. Отправить письмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='send']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button2_close"))).click();

        // 5. Verify, что письмо появилось в папке Входящие
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_INBOX))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_INBOX));
        int receivedMessagesAfter = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();
        System.out.println("Received messages after: " + receivedMessagesAfter);
        assertThat(receivedMessagesAfter).isEqualTo(receivedMessagesBefore + 1);

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
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

        // 7. Удалить письмо
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button2_delete"))).click();

        // 8. Verify что письмо появилось в папке Корзина
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LEFT_MENU_TRASH))).click();
        wait.until(ExpectedConditions.titleContains(PAGE_TITLE_TRASH));

        int deletedMessagesAfter = wait.until(ExpectedConditions
            .presenceOfAllElementsLocatedBy(By.cssSelector(LETTER_LIST_ITEM))).size();
        System.out.println("Deleted messages after: " + deletedMessagesAfter);
        assertThat(deletedMessagesAfter).isEqualTo(deletedMessagesBefore + 1);

        // 9. Выйти из учётной записи
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.ph-avatar-img"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .cssSelector("[data-testid='whiteline-account-exit']"))).click();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
