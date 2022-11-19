package QKART_TESTNG;


import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass implements ITestListener {
    WebDriver driver; 

    public void onStart(ITestContext context) {
        System.out.println("onStart method started");      
    }

    public void onFinish(ITestContext context) {
       System.out.println("onFinish method started");
    }

    public void onTestStart(ITestResult result) {
        System.out.println("New Test Started" +result.getName());
        QKART_Tests.takeScreenshot("TC_START", result.getName());

    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("onTestSuccess Method" +result.getName());
        QKART_Tests.takeScreenshot("TC_SUCCESS", result.getName());
        
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("onTestFailure Method" +result.getName());
        QKART_Tests.takeScreenshot("TC_FAILURE", result.getName());
    }

}
