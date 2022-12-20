package ru.levelp.at.homework5.steps;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;

public class AssertionStep extends BaseStep {

    public AssertionStep(WebDriver driver) {
        super(driver);
    }

    public void authorizedAsGivenUser(String login) {
        assertThat(mainPage.getActualUsername()).isEqualTo(login);
    }

    public void checkDraftMessageAddresseeSubjectAndText(String addressee, String subject, String text) {
        mainPage.clickOnFirstLetterInListOfLetters();
        assertThat(mainPage.getDraftActualAddressee()).isEqualTo(addressee);
        assertThat(mainPage.getDraftActualSubject()).isEqualTo(subject);
        assertThat(mainPage.getActualMessageText()).isEqualTo(text);
    }

    public void checkSentOrReceivedMessageAddresseeSubjectAndText(String addressee, String subject, String text) {
        mainPage.clickOnFirstLetterInListOfLetters();
        assertThat(mainPage.getActualAddressee()).isEqualTo(addressee);
        assertThat(mainPage.getActualSubject()).isEqualTo(subject);
        assertThat(mainPage.getActualMessageText()).isEqualTo(text);
    }
}
