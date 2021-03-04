package config;


import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class SeleniumConfig {

    private WebDriver driver;
    static String driverName;

    public SeleniumConfig() {
        //Capabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        System.out.println("Создаем драйвер");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    static {
        driverName = "chromedriver";
        if(System.getProperty("os.name").toLowerCase().contains("win")) {
            driverName += ".exe";
        }
        System.setProperty("webdriver.chrome.driver", findFile(driverName));
    }

    static private String findFile(String filename) {
        String paths[]= {"", "bin/", "target/classes"};
        for (String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        return "";
    }

    public void close() {
        driver.close();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    public WebDriver getDriver() {
        return driver;
    }

}
