package ru.levelp.at.homework7;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты первого упражнения домашней работы №3 по Selenium")
@Feature("Черновики")
public class FirstExerciseWithStepsTest extends BaseSeleniumTest {

    private static final String SUBJECT = "FirstExercise. Тема письма";
    private static final String MESSAGE_TEXT = "FirstExercise. Текст для заполнения тела письма.";

    @Test
    @DisplayName("Сохранение сообщения в черновиках и отправка сообщения из черновиков")
    @Story("Создание письма")
    @Story("Сохранение черновика письма")
    @Story("Отправка черновика письма")
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

    @Test
    @DisplayName("Сохранение сообщения в черновиках и отправка сообщения из черновиков с последующим падением теста")
    @Story("Создание письма")
    @Story("Сохранение черновика письма")
    @Story("Отправка черновика письма")
    void saveAndSendDraftMessageAndFail() {

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

        // Шаг добавлен для того, чтобы тест упал
        actionStep.createLetter(MAIL_RU_LOGIN, SUBJECT, MESSAGE_TEXT);
    }
}
