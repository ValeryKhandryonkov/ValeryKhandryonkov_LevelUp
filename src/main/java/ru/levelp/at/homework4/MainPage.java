package ru.levelp.at.homework4;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String PAGE_TITLE_SENT = "Отправленные - Почта Mail.ru";
    private static final String PAGE_TITLE_DRAFT = "Черновики - Почта Mail.ru";
    private static final String LETTER_LIST_ITEM = "a.js-letter-list-item";

    @FindBy(css = ".ph-project-promo-close-icon")
    private WebElement promoPopupCloseButton;

    @FindBy(css = "img.ph-avatar-img")
    private WebElement rightMenuButton;

    @FindBy(css = "a.compose-button")
    private WebElement composeLetterButton;

    @FindBy(xpath = "//div[@data-type='to']//input")
    private WebElement addresseeField;

    @FindBy(css = "input[name='Subject']")
    private WebElement subjectField;

    @FindBy(css = "div[role='textbox']")
    private WebElement messageTextField;

    @FindBy(css = "button[data-test-id='save']")
    private WebElement saveButton;

    @FindBy(css = "button[title='Закрыть']")
    private WebElement composeLetterCloseButton;

    @FindBy(css = "[href='/sent/?']")
    private WebElement leftMenuSentButton;

    @FindBy(css = LETTER_LIST_ITEM)
    private List<WebElement> listOfLetters;

    @FindBy(css = "[href='/drafts/?']")
    private WebElement leftMenuDraftsButton;

    @FindBy(css = "a.js-letter-list-item:nth-of-type(1)")
    private WebElement firstLetterInListOfLetters;

    @FindBy(xpath = "//div[@data-type='to']//span[contains(@class, 'text')]")
    private WebElement draftActualAddressee;

    @FindBy(xpath = "//div[contains(@class, 'compose-app_window')]//input[@name='Subject']")
    private WebElement draftActualSubject;

    @FindBy(css = "div.js-readmsg-msg > div > div > div > :first-child")
    private WebElement draftActualMessageText;

    @FindBy(css = "[data-test-id='send']")
    private WebElement sendButton;

    @FindBy(css = ".button2_close")
    private WebElement letterSentCloseButton;

    @FindBy(css = "[data-testid='whiteline-account-exit']")
    private WebElement logoutButton;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        PageFactory.initElements(driver, this);
    }

    public void closePromoPopup() {
        wait.until(ExpectedConditions.visibilityOf(promoPopupCloseButton)).click();
    }

    public String getActualUsername() {
        return rightMenuButton.getAttribute("alt");
    }

    public void clickComposeLetterButton() {
        wait.until(ExpectedConditions.visibilityOf(composeLetterButton)).click();
    }

    public void enterAddresseeToAddresseeField(String addressee) {
        wait.until(ExpectedConditions.visibilityOf(addresseeField)).sendKeys(addressee);
    }

    public void enterSubjectToSubjectField(String subject) {
        wait.until(ExpectedConditions.visibilityOf(subjectField)).sendKeys(subject);
    }

    public void enterMessageTextToMessageTextField(String text) {
        wait.until(ExpectedConditions.visibilityOf(messageTextField)).sendKeys(text);
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.visibilityOf(saveButton)).click();
    }

    public void clickComposeLetterCloseButton() {
        wait.until(ExpectedConditions.visibilityOf(composeLetterCloseButton)).click();
    }

    public void clickLeftMenuSentButton() {
        wait.until(ExpectedConditions.visibilityOf(leftMenuSentButton)).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_SENT));
    }

    public int getListOfLettersCount() {
        return listOfLetters.size();
    }

    public void clickLeftMenuDraftsButton() {
        wait.until(ExpectedConditions.visibilityOf(leftMenuDraftsButton)).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
    }

    public void clickOnFirstLetterInListOfLetters() {
        wait.until(ExpectedConditions.elementToBeClickable(firstLetterInListOfLetters)).click();
    }

    public String getDraftActualAddressee() {
        return wait.until(ExpectedConditions.visibilityOf(draftActualAddressee)).getText();
    }

    public String getDraftActualSubject() {
        return wait.until(ExpectedConditions.visibilityOf(draftActualSubject)).getAttribute("value");
    }

    public String getDraftActualMessageText() {
        return wait.until(ExpectedConditions.visibilityOf(draftActualMessageText)).getText();
    }

    public void clickSendButton() {
        wait.until(ExpectedConditions.visibilityOf(sendButton)).click();
    }

    public void clickLetterSentCloseButton() {
        wait.until(ExpectedConditions.visibilityOf(letterSentCloseButton)).click();
    }

    public void clickRightMenuButton() {
        wait.until(ExpectedConditions.visibilityOf(rightMenuButton)).click();
    }

    public void clickLogoutButton() {
        wait.until(ExpectedConditions.visibilityOf(logoutButton)).click();
    }

    public void waitUntilNumberOfLettersDecreaseByOne(int numberOfLettersBefore) {
        wait.until(ExpectedConditions.numberOfElementsToBe(By
            .cssSelector(LETTER_LIST_ITEM), numberOfLettersBefore - 1));
    }

    public void waitUntilNumberOfLettersIncreaseByOne(int numberOfLettersBefore) {
        wait.until(ExpectedConditions.numberOfElementsToBe(By
            .cssSelector(LETTER_LIST_ITEM), numberOfLettersBefore + 1));
    }
}
