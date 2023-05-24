package com.cucumber.definitions;

import com.common.config.Configuration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import com.common.ui_engine.page_objects.pages.LoginPage;

import static com.common.BaseTest.*;

@Epic("Regression Tests")
@Feature("Login")
public class LoginPageSteps {

    static String URL;
    LoginPage loginPage;

    @Given("Browser is launched")
    public void browser_is_launched() {
        setUpReporter();
        setUp("browser", false);
        loginPage = new LoginPage(webDriver);
        URL = Configuration.url;
        webDriver.get(URL);
    }

    @And("User clicks login")
    public void user_clicks_login() {
        loginPage.clickLogin();
    }

    @And("User clicks logout")
    public void user_clicks_logout() {
        loginPage = new LoginPage(webDriver);
        loginPage.clickLogout();
    }

    @And("User enters username and password")
    public void user_enters_username_and_password() {
        loginPage.setUserName(Configuration.userName);
        loginPage.setPassword(Configuration.password);
    }

    @Then("check user is logged in")
    public void check_user_is_logged_in() {
        Assert.assertEquals(loginPage.logoutButtonIsDisplayed(), true, "Verify user is logged in");
        Assert.assertEquals(loginPage.getUserName(), Configuration.userName, "Verify user name is correct");
    }

    @And("User enters invalid username and password")
    public void user_enters_invalid_username_and_password() {
        loginPage.setUserName(RandomStringUtils.randomAlphabetic(5));
        loginPage.setPassword(RandomStringUtils.randomAlphabetic(5));
    }

    @Then("check user is not logged in")
    public void check_user_is_not_logged_in() {
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid username or password!", "User is logged in or error message is absent/wrong");
    }

    @And("Close browser")
    public void close_browser() {
        flushReport();
    }
}

