package ru.levelp.at.homework5.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.levelp.at.homework5.LoginRegistrationPage;
import ru.levelp.at.homework5.MainPage;

public class ActionStep extends BaseStep {

    public ActionStep(WebDriver driver) {
        super(driver);
    }

    public void loginToMailRuService(String login, String password) {
        ru.levelp.at.homework5.LoginRegistrationPage loginRegistrationPage = new LoginRegistrationPage(driver);

        loginRegistrationPage.open();
        loginRegistrationPage.clickOnLoginButton();

        loginRegistrationPage.enterUsernameToUsernameField(login);
        loginRegistrationPage.sendEnterKeyToUsernameField();

        loginRegistrationPage.enterPasswordToPasswordField(password);
        loginRegistrationPage.sendEnterKeyToPasswordField();

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);

        mainPage.closePromoPopup();
    }

    public void createLetter(String addressee, String subject, String messageText) {
        mainPage.clickComposeLetterButton();
        mainPage.enterAddresseeToAddresseeField(addressee);
        mainPage.enterSubjectToSubjectField(subject);
        mainPage.enterMessageTextToMessageTextField(messageText);
    }

    public void saveLetter() {
        mainPage.clickSaveButton();
        mainPage.clickComposeLetterCloseButton();
    }

    public void sendLetter() {
        mainPage.clickSendButton();
        mainPage.clickLetterSentCloseButton();
    }

    public void logout() {
        mainPage.clickRightMenuButton();
        mainPage.clickLogoutButton();
    }

    public int getListOfSentLettersCount() {
        mainPage.clickLeftMenuSentButton();
        return mainPage.getListOfLettersCount();
    }

    public int getListOfDraftLettersCount() {
        mainPage.clickLeftMenuDraftsButton();
        return mainPage.getListOfLettersCount();
    }

    public void verifyNumberOfDraftLettersDecreaseByOne(int numberOfLettersBefore) {
        mainPage.clickLeftMenuDraftsButtonAndWaitForNumberOfLettersDecreaseByOne(numberOfLettersBefore);
    }

    public void verifyNumberOfSentLettersIncreaseByOne(int numberOfLettersBefore) {
        mainPage.clickLeftMenuSentButtonAndWaitForNumberOfLettersIncreaseByOne(numberOfLettersBefore);
    }
}
