package com.common.config;

import com.common.ui_engine.browser_factory.BrowserType;
import com.common.ui_engine.browser_factory.DriverManagerFactory;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;

public class Configuration {

    public static void setBrowser(String browser) {
        Configuration.browser = DriverManagerFactory.returnBrowserType(browser);
    }

    public static void setIsHeadless(boolean isHeadless) {
        Configuration.isHeadless = isHeadless;
    }

    public static BrowserType browser;
    public static boolean isHeadless;
    public static String url;
    public static String userName;
    public static String password;
    public static String env;

}
