package com.qa.atliq.practical.runners;
import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.qa.atliq.practical.step.definitions.SauceDemo;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

@CucumberOptions(
	features = "src/test/resources/FeatureFiles/SauceDemo.feature", 
	glue = {"com.qa.atliq.practical.step.definitions" }, 
	plugin = { "pretty", "html:target/HTMlReports/report.html",
			"json:target/JSONReports/report.json" ,
			"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
	tags = "@positive or @negative"
)

public class TestRunner extends AbstractTestNGCucumberTests {
	
	@BeforeMethod
	@Parameters("browser")
    public void setUp(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            SauceDemo.driver = new ChromeDriver();
            System.out.println("Chrome is Launched");
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            SauceDemo.driver = new EdgeDriver();
            System.out.println("Edge is Launched");
        }
        SauceDemo.driver.manage().window().maximize();
        SauceDemo.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        SauceDemo.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }
	
	@AfterMethod
	public void tearDown() {
		SauceDemo.driver.close();
		SauceDemo.driver.quit();
	}

}
