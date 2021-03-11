package config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import properties.Proper;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SeleniumConfig {

    private WebDriver driver;
    static String driverName;

    private static Logger log = LogManager.getLogger(SeleniumConfig.class);

    static {
        driverName = "chromedriver";
        if(System.getProperty("os.name").toLowerCase().contains("win")) {
            driverName += ".exe";
        }
        System.setProperty("webdriver.chrome.driver", findFile(driverName));
    }

    public SeleniumConfig() throws IOException {
        //Capabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        if (Proper.getProps().getProperty("headless").equals("true")) {
            options.addArguments("--headless");
            log.info("Headless mode selenium");
        }
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
