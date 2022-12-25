package ru.levelp.at.homework7.steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class ActionStep extends BaseStep {

    public ActionStep(WebDriver driver) {
        super(driver);
    }

    @Step("Авторизоваться в почтовом сервисе Mail.ru под учёткой {login}")
    public void loginToMailRuService(String login, String password) {

        loginRegistrationPage.open();
        loginRegistrationPage.clickOnLoginButton();

        loginRegistrationPage.enterUsernameToUsernameField(login);
        loginRegistrationPage.sendEnterKeyToUsernameField();

        loginRegistrationPage.enterPasswordToPasswordField(password);
        loginRegistrationPage.sendEnterKeyToPasswordField();

        mainPage.closePromoPopup();
    }

    @Step("Нажать кнопку создания письма и заполнить адресата, тему и тело письма")
    public void createLetter(String addressee, String subject, String messageText) {
        mainPage.clickComposeLetterButton();
        mainPage.enterAddresseeToAddresseeField(addressee);
        mainPage.enterSubjectToSubjectField(subject);
        mainPage.enterMessageTextToMessageTextField(messageText);
    }

    @Step("Нажать кнопку сохранения письма и закрыть окно создания письма")
    public void saveLetter() {
        mainPage.clickSaveButton();
        mainPage.clickComposeLetterCloseButton();
    }

    @Step("Нажать кнопку отправки письма и закрыть окно отправленного письма")
    public void sendLetter() {
        mainPage.clickSendButton();
        mainPage.clickLetterSentCloseButton();
    }

    @Step("Выйти из учётной записи")
    public void logout() {
        mainPage.clickRightMenuButton();
        mainPage.clickLogoutButton();
    }

    @Step("Определить количество писем в папке 'Отправленные'")
    public int getListOfSentLettersCount() {
        mainPage.clickLeftMenuSentButton();
        return mainPage.getListOfLettersCount();
    }

    @Step("Определить количество писем в папке 'Черновики '")
    public int getListOfDraftLettersCount() {
        mainPage.clickLeftMenuDraftsButton();
        return mainPage.getListOfLettersCount();
    }

    @Step("Определить количество писем в папке 'Тест'")
    public int getListOfTestLettersCount() {
        mainPage.clickLeftMenuTestButton();
        return mainPage.getListOfLettersCount();
    }

    @Step("Определить количество писем в папке 'Входящие'")
    public int getListOfReceivedLettersCount() {
        mainPage.clickLeftMenuInboxButton();
        return mainPage.getListOfLettersCount();
    }

    @Step("Определить количество писем в папке 'Корзина'")
    public int getListOfDeletedLettersCount() {
        mainPage.clickLeftMenuTrashButton();
        return mainPage.getListOfLettersCount();
    }

    @Step("Проверить, что количество писем в папке 'Черновики' уменьшилось на 1")
    public void verifyNumberOfDraftLettersDecreaseByOne(int numberOfLettersBefore) {
        mainPage.clickLeftMenuDraftsButtonAndWaitForNumberOfLettersDecreaseByOne(numberOfLettersBefore);
    }

    @Step("Проверить, что количество писем в папке 'Отправленные' увеличилось на 1")
    public void verifyNumberOfSentLettersIncreaseByOne(int numberOfLettersBefore) {
        mainPage.clickLeftMenuSentButtonAndWaitForNumberOfLettersIncreaseByOne(numberOfLettersBefore);
    }

    @Step("Проверить, что количество писем в папке 'Тест' увеличилось на 1")
    public void verifyNumberOfTestLettersIncreaseByOne(int numberOfLettersBefore) {
        mainPage.clickLeftMenuTestButtonAndWaitForNumberOfLettersIncreaseByOne(numberOfLettersBefore);
    }

    @Step("Проверить, что количество писем в папке 'Входящие' увеличилось на 1")
    public void verifyNumberOfReceivedLettersIncreaseByOne(int numberOfLettersBefore) {
        mainPage.clickLeftMenuInboxButtonAndWaitForNumberOfLettersIncreaseByOne(numberOfLettersBefore);
    }

    @Step("Проверить, что количество писем в папке 'Корзина' увеличилось на 1")
    public void verifyNumberOfDeletedLettersIncreaseByOne(int numberOfLettersBefore) {
        mainPage.clickLeftMenuTrashButtonAndWaitForNumberOfLettersIncreaseByOne(numberOfLettersBefore);
    }

    @Step("Нажать кнопку удаления письма")
    public void deleteLetter() {
        mainPage.clickDeleteButton();
    }
}
