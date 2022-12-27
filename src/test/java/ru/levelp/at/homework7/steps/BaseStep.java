package ru.levelp.at.homework7.steps;

import org.openqa.selenium.WebDriver;
import ru.levelp.at.homework7.LoginRegistrationPage;
import ru.levelp.at.homework7.MainPage;

public class BaseStep {

    protected final WebDriver driver;

    protected LoginRegistrationPage loginRegistrationPage;
    protected MainPage mainPage;

    protected BaseStep(WebDriver driver) {
        this.driver = driver;
        loginRegistrationPage = new LoginRegistrationPage(driver);
        mainPage = new MainPage(driver);
    }
}
