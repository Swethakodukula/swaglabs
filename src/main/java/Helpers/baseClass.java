package Helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class baseClass {

    public WebDriver driver = null;
    public Properties prop;
    protected ExtentReports extent;
    protected ExtentTest test;



    @BeforeClass
    public void setBrowser() {
        //extent report in a
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        prop = new Properties();
        try{

            FileInputStream io = new FileInputStream("src/main/resources/config.properties");
            prop.load(io);
            io.close();
        }
        catch (IOException e){
           e.printStackTrace();
        }
        String browser = prop.getProperty("browser");
        if (browser.equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("Edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        clearCookie();
        maximize();
        initWait(30);
    }

    public void maximize() {
        driver.manage().window().maximize();
    }

    public void clearCookie() {
        driver.manage().deleteAllCookies();
    }

    public void initWait(int wait) {
        driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
    }

    public void gotoUrl(String Url) {
        driver.get(Url);
    }

    @AfterClass
    public void exit(ITestResult result) {
        // Log test status and additional details
        if (result.getStatus() == ITestResult.FAILURE) {
            //test.log(Status.FAIL, "Test Case Failed: " + result.getThrowable().getMessage());
            attachScreenshot();
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case Skipped: " + result.getThrowable().getMessage());
        }else{
            //test.log(Status.PASS, "Test Case PASSED: ");
            attachScreenshot();
        }
        extent.flush();

        if(driver!=null) {
            driver.close();
        }

    }

    private void attachScreenshot() {
        // Take a screenshot and attach it to the Extent Report
        try {
            String screenshotPath = takeScreenshot();
            test.addScreenCaptureFromPath(screenshotPath, "Screenshot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String takeScreenshot() throws IOException {
        // Capture screenshot and return the file path
        // Note: You may need to adapt this method based on your WebDriver and screenshot capture logic
        // This is just a basic example using the Selenium WebDriver TakesScreenshot interface

        // Take screenshot
        String screenshotPath = "path/to/screenshots/screenshot.png";
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Copy screenshot to the specified path
        FileUtils.copyFile(screenshotFile, new File(screenshotPath));

        return screenshotPath;
    }
}

