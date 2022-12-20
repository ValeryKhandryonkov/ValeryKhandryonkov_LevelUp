package ru.levelp.at.homework5;

import org.junit.jupiter.api.Test;


public class ThirdExerciseWithStepsTest extends BaseSeleniumTest {

    private static final String SUBJECT = "ThirdExercise. Тема письма";
    private static final String MESSAGE_TEXT = "ThirdExercise. Текст для заполнения тела письма.";

    @Test
    void sendNewMessageForInboxFolderAndDeleteMessage() {

        // 1. Войти в почту
        actionStep.loginToMailRuService(MAIL_RU_LOGIN, MAIL_RU_PASSWORD);

        // 2. Assert, что вход выполнен успешно
        assertionStep.authorizedAsGivenUser(MAIL_RU_LOGIN);

        final int receivedMessagesBefore = actionStep.getListOfReceivedLettersCount();
        final int deletedMessagesBefore = actionStep.getListOfDeletedLettersCount();

        // 3. Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)
        actionStep.createLetter(MAIL_RU_LOGIN, SUBJECT, MESSAGE_TEXT);

        // 4. Отправить письмо
        actionStep.sendLetter();

        // 5. Verify, что письмо появилось в папке Входящие
        actionStep.verifyNumberOfReceivedLettersIncreaseByOne(receivedMessagesBefore);

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        assertionStep.checkSentOrReceivedMessageAddresseeSubjectAndText(MAIL_RU_LOGIN, SUBJECT, MESSAGE_TEXT);

        // 7. Удалить письмо
        actionStep.deleteLetter();

        // 8. Verify что письмо появилось в папке Корзина
        actionStep.verifyNumberOfDeletedLettersIncreaseByOne(deletedMessagesBefore);

        // 9. Выйти из учётной записи
        actionStep.logout();
    }
}
