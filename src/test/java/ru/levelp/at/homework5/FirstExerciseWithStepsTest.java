package ru.levelp.at.homework5;

import org.junit.jupiter.api.Test;

public class FirstExerciseWithStepsTest extends BaseSeleniumTest {

    private static final String SUBJECT = "FirstExercise. Тема письма";
    private static final String MESSAGE_TEXT = "FirstExercise. Текст для заполнения тела письма.";

    @Test
    void saveAndSendDraftMessage() {

        // 1. Войти в почту
        actionStep.loginToMailRuService(MAIL_RU_LOGIN, MAIL_RU_PASSWORD);

        // 2. Assert, что вход выполнен успешно
        assertionStep.authorizedAsGivenUser(MAIL_RU_LOGIN);

        // 3. Создать новое письмо (заполнить адресата, тему письма и тело)
        actionStep.createLetter(MAIL_RU_LOGIN, SUBJECT, MESSAGE_TEXT);

        // 4. Сохранить его как черновик
        actionStep.saveLetter();

        // 5. Verify, что письмо сохранено в черновиках
        final int sentMessagesBefore = actionStep.getListOfSentLettersCount();
        final int draftMessagesBefore = actionStep.getListOfDraftLettersCount();

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        assertionStep.checkDraftMessageAddresseeSubjectAndText(MAIL_RU_LOGIN, SUBJECT, MESSAGE_TEXT);

        // 7. Отправить письмо
        actionStep.sendLetter();

        // 8. Verify, что письмо исчезло из черновиков
        actionStep.verifyNumberOfDraftLettersDecreaseByOne(draftMessagesBefore);

        // 9. Verify, что письмо появилось в папке отправленные
        actionStep.verifyNumberOfSentLettersIncreaseByOne(sentMessagesBefore);

        // 10. Выйти из учётной записи
        actionStep.logout();
    }
}
