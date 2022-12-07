package ru.levelp.at.homework4;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;


public class ThirdExerciseWithPageObjectTest extends BaseSeleniumTest {

    private static final String SUBJECT = "ThirdExercise. Тема письма";
    private static final String MESSAGE_TEXT = "ThirdExercise. Текст для заполнения тела письма.";

    @Test
    void sendNewMessageForInboxFolderAndDeleteMessage() {

        // 1. Войти в почту
        LoginRegistrationPage loginRegistrationPage = new LoginRegistrationPage(driver);

        loginRegistrationPage.open();
        loginRegistrationPage.clickOnLoginButton();

        loginRegistrationPage.enterUsernameToUsernameField(MAIL_RU_LOGIN);
        loginRegistrationPage.sendEnterKeyToUsernameField();

        loginRegistrationPage.enterPasswordToPasswordField(MAIL_RU_PASSWORD);
        loginRegistrationPage.sendEnterKeyToPasswordField();

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);

        mainPage.closePromoPopup();

        // 2. Assert, что вход выполнен успешно
        Assertions.assertThat(mainPage.getActualUsername()).isEqualTo(MAIL_RU_LOGIN);

        mainPage.clickLeftMenuInboxButton();
        final int receivedMessagesBefore = mainPage.getListOfLettersCount();

        mainPage.clickLeftMenuTrashButton();
        final int deletedMessagesBefore = mainPage.getListOfLettersCount();

        // 3. Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        mainPage.clickComposeLetterButton();

        mainPage.enterAddresseeToAddresseeField(MAIL_RU_LOGIN);
        mainPage.enterSubjectToSubjectField(SUBJECT);
        mainPage.enterMessageTextToMessageTextField(MESSAGE_TEXT);

        // 4. Отправить письмо
        mainPage.clickSendButton();
        mainPage.clickLetterSentCloseButton();

        // 5. Verify, что письмо появилось в папке Входящие
        mainPage.clickLeftMenuInboxButton();
        mainPage.waitUntilNumberOfLettersIncreaseByOne(receivedMessagesBefore);

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        mainPage.clickOnFirstLetterInListOfLetters();

        Assertions.assertThat(mainPage.getActualAddressee()).isEqualTo(MAIL_RU_LOGIN);
        Assertions.assertThat(mainPage.getActualSubject()).isEqualTo(SUBJECT);
        Assertions.assertThat(mainPage.getActualMessageText()).isEqualTo(MESSAGE_TEXT);

        // 7. Удалить письмо
        mainPage.clickDeleteButton();

        // 8. Verify что письмо появилось в папке Корзина
        mainPage.clickLeftMenuTrashButton();
        mainPage.waitUntilNumberOfLettersIncreaseByOne(deletedMessagesBefore);

        // 9. Выйти из учётной записи
        mainPage.clickRightMenuButton();
        mainPage.clickLogoutButton();
    }
}
