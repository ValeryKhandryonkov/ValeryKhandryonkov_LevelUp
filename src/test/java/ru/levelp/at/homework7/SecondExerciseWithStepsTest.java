package ru.levelp.at.homework7;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тесты второго упражнения домашней работы №3 по Selenium")
@Feature("Пользовательская папка")
public class SecondExerciseWithStepsTest extends BaseSeleniumTest {

    private static final String SUBJECT = "SecondExercise. Тема письма - Тест";
    private static final String MESSAGE_TEXT = "SecondExercise. Текст для заполнения тела письма.";

    @Test
    @DisplayName("Создание и отправка созданого письма")
    @Story("Создание письма")
    @Story("Отправка созданного письма")
    void sendNewMessageForTestFolder() {

        // 1. Войти в почту
        actionStep.loginToMailRuService(MAIL_RU_LOGIN, MAIL_RU_PASSWORD);

        // 2. Assert, что вход выполнен успешно
        assertionStep.authorizedAsGivenUser(MAIL_RU_LOGIN);

        final int sentMessagesBefore = actionStep.getListOfSentLettersCount();
        final int testMessagesBefore = actionStep.getListOfTestLettersCount();

        // 3. Создать новое письмо (заполнить адресата, тему письма и тело)
        actionStep.createLetter(MAIL_RU_LOGIN, SUBJECT, MESSAGE_TEXT);

        // 4. Отправить письмо
        actionStep.sendLetter();

        // 5. Verify, что письмо появилось в папке отправленные
        actionStep.verifyNumberOfSentLettersIncreaseByOne(sentMessagesBefore);

        // 6. Verify, что письмо появилось в папке «Тест»
        actionStep.verifyNumberOfTestLettersIncreaseByOne(testMessagesBefore);

        // 7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        assertionStep.checkSentOrReceivedMessageAddresseeSubjectAndText(MAIL_RU_LOGIN, SUBJECT, MESSAGE_TEXT);

        // 8. Выйти из учётной записи
        actionStep.logout();
    }
}
