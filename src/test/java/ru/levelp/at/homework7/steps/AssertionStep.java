package ru.levelp.at.homework7.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class AssertionStep extends BaseStep {

    public AssertionStep(WebDriver driver) {
        super(driver);
    }

    @Step("Проверить, что успешно авторизовались под пользователем {login}")
    public void authorizedAsGivenUser(String login) {
        assertThat(mainPage.getActualUsername()).isEqualTo(login);
    }

    @Step("Проверить у черновика, что адресат, тема и тело письма соответсвуют заданным")
    public void checkDraftMessageAddresseeSubjectAndText(String addressee, String subject, String text) {
        mainPage.clickOnFirstLetterInListOfLetters();
        assertThat(mainPage.getDraftActualAddressee()).isEqualTo(addressee);
        assertThat(mainPage.getDraftActualSubject()).isEqualTo(subject);
        assertThat(mainPage.getActualMessageText()).isEqualTo(text);
    }

    @Step("Проверить у входящего письма, что адресат, тема и тело письма соответсвуют заданным")
    public void checkSentOrReceivedMessageAddresseeSubjectAndText(String addressee, String subject, String text) {
        mainPage.clickOnFirstLetterInListOfLetters();
        assertThat(mainPage.getActualAddressee()).isEqualTo(addressee);
        assertThat(mainPage.getActualSubject()).isEqualTo(subject);
        assertThat(mainPage.getActualMessageText()).isEqualTo(text);
    }
}
