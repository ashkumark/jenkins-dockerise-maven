package com.m2at.utils;

import java.util.Collections;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

	WebDriver driver;
	ChromeOptions options;

	public void setDriver(String browserType) {
		if (driver == null) {
			switch (browserType) {
			case "chrome":
				options = new ChromeOptions();

				options.setBinary("/opt/google/chrome/google-chrome");

				options.addArguments("--no-sandbox"); // Bypass OS security model
				options.addArguments("--remote-debugging-port=9222");
				options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
				options.addArguments("start-maximized"); // open Browser in maximized mode
				options.addArguments("disable-infobars"); // disabling infobars
				options.addArguments("--disable-extensions"); // disabling extensions
				options.addArguments("--disable-gpu"); // applicable to windows os only

				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);

				driver = new ChromeDriver();
				break;
			case "chromeheadless":
				options = new ChromeOptions();
				options.setBinary("/opt/google/chrome/google-chrome");

				options.addArguments("--no-sandbox"); // Bypass OS security model
				options.addArguments("--remote-debugging-port=9222");
				options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
				options.addArguments("start-maximized"); // open Browser in maximized mode
				options.addArguments("disable-infobars"); // disabling infobars
				options.addArguments("--disable-extensions"); // disabling extensions
				options.addArguments("--disable-gpu"); // applicable to windows os only

				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);
				options.addArguments("--headless");

				driver = new ChromeDriver(options);
				break;
			case "firefox":
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "firefoxLog");
				java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

				driver = new FirefoxDriver();

				break;
			case "firefoxheadless":
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "firefoxLog");
				java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

				FirefoxOptions foptions = new FirefoxOptions();
				foptions.setHeadless(true);

				driver = new FirefoxDriver(foptions);

				break;
			default:
				System.out.println("Driver not initialised");
				throw new RuntimeException("Unsupported browser");
			}
		}
	}

	public WebDriver initialiseWebDriver(String browserType) {

		System.setProperty("webdriver.chrome.driver", "/chromedriver/chromedriver");

		setDriver(browserType);

		return driver;
	}
}
