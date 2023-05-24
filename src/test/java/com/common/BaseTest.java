package com.common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.common.config.ConfigReader;
import com.common.config.Configuration;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.common.ui_engine.browser_factory.DriverManager;
import com.common.ui_engine.browser_factory.DriverManagerFactory;
import com.common.ui_engine.utils.JsWaiter;
import com.common.ui_engine.utils.SimpleAPI;
import com.common.ui_engine.utils.Utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BaseTest extends SimpleAPI{

    protected static final Logger LOG = LogManager.getLogger(BaseTest.class);
    public static WebDriver webDriver;
    static DriverManager driverManager;
    static ExtentReports report;
    static ExtentTest test;
    static ExtentSparkReporter spark;
    static JsWaiter jsWaiter;
    private static ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod
    public void beforeMethod(Method method, Object[] testData)
    {
        Test a = method.getAnnotation(Test.class);
        String name = a.testName();

        LOG.info("Starting test " + name);

        test = report.createTest(name);
        test.info("Starting test " + name);
        testName.set(name);
    }

    @BeforeSuite
    public static void setUpReporter()
    {
        ConfigReader.readConfiguration();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy_HH_mm_ss");
        String date  = simpleDateFormat.format(new Date());

        report = new ExtentReports();
        spark = new ExtentSparkReporter("./reports/test_run_" + date + ".html");
        spark.config().setDocumentTitle("Selenium Report");
        spark.config().setReportName("Functional Test");
        spark.config().setTheme(Theme.DARK);

        report.attachReporter(spark);

        report.setSystemInfo("Environment", Configuration.env);
        report.setSystemInfo("User", Configuration.userName);
        report.setSystemInfo("Browser", Configuration.browser.name());
    }

    @BeforeAll
    @Parameters({"browser","headless"})
    public static void setUp(@Optional("none") String browser, @Optional("false") boolean headless) {

        if (Configuration.isHeadless != headless && headless) Configuration.setIsHeadless(headless);

        driverManager = DriverManagerFactory.getManager(Configuration.browser);
        webDriver = driverManager.createDriver().get();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        jsWaiter = new JsWaiter(webDriver);
    }

    @AfterMethod
    public void getResult(ITestResult result)
    {
        LOG.info(result.getName() + " execution is finished with result " + result.getStatus());

        if(result.getStatus() == ITestResult.FAILURE)
        {
            test.log(Status.FAIL, result.getThrowable());
            Utils.allureScreenshot(webDriver, result.getName());
            Utils.captureScreenshot(webDriver, result.getName());
            test.fail(MediaEntityBuilder.createScreenCaptureFromPath("." + Utils.scrPath).build());
        }
        else if(result.getStatus() == ITestResult.SKIP)
        {
            test.log(Status.SKIP, "Test Skipped: " + result.getTestName());
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            test.log(Status.PASS, "Test Passed: " + testName.get());
        }
    }

    @AfterAll
    public static void flushReport()
    {
        report.flush();
        webDriver.quit();
    }

    @Override
    public WebDriver getWebDriver() {
        return webDriver;
    }

}
