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

    public void checkSavedMessageAddresseeSubjectAndText(String addressee, String subject, String text) {
        mainPage.clickOnFirstLetterInListOfLetters();
        assertThat(mainPage.getDraftActualAddressee()).isEqualTo(addressee);
        assertThat(mainPage.getDraftActualSubject()).isEqualTo(subject);
        assertThat(mainPage.getActualMessageText()).isEqualTo(text);
    }
}
