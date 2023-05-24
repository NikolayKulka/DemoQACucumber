package com.common.ui_engine.page_objects.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.common.ui_engine.page_objects.common_objects.BasePage;

public class LoginPage extends BasePage {

    @FindBy(id = "userName")
    private WebElement userNameField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(id = "login")
    private WebElement loginButton;
    @FindBy(id = "userName-value")
    private WebElement userNameValue;
    @FindBy(xpath = "//button[@id = 'submit'][contains(.,'Log out')]")
    private WebElement logoutButton;
    @FindBy(xpath = "//div[@id = 'output']//p")
    private WebElement errorMessage;


    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void setUserName(String username) {
        userNameField.clear();
        userNameField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void clickLogout() {
        logoutButton.click();
    }

    public String getUserName() {
        return userNameValue.getText();
    }

    public boolean logoutButtonIsDisplayed() {
        return logoutButton.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}