package ru.levelp.at.homework4;

import java.time.Duration;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginRegistrationPage {

    private static final String MAIL_RU_URL = "https://mail.ru/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "[data-testid='enter-mail-primary']")
    private WebElement loginButton;

    @FindBy(css = "iframe[class='ag-popup__frame__layout__iframe']")
    private WebElement loginIntoAccountFrame;

    @FindBy(css = "input[name='username']")
    private WebElement usernameField;

    @FindBy(css = "input[name='password']")
    private WebElement passwordField;

    public LoginRegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(MAIL_RU_URL);
    }

    public void clickOnLoginButton() {
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }

    public void switchToLoginIntoAccountFrame() {
        driver.switchTo().frame(loginIntoAccountFrame);
    }

    public void enterUsernameToUsernameField(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
    }

    public void sendEnterKeyToUsernameField() {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(Keys.ENTER);
    }

    public void enterPasswordToPasswordField(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    public void sendEnterKeyToPasswordField() {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(Keys.ENTER);
    }
}
