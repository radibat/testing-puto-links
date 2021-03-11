import config.SeleniumConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseTestLink {
    private SeleniumConfig config;
    private CsvWriter newCSV;

    private static Logger log = LogManager.getLogger(BaseTestLink.class);

    public BaseTestLink(String url) throws IOException {
        config = new SeleniumConfig();
        config.getDriver().get(url);
    }

    public void GetSocialSections(String page) throws IOException {
        log.info("Start getting social sections");
        config.getDriver().get(page);
        List<String[]> sections = new ArrayList<>();
        List<WebElement> elementsSections = this.config.getDriver().findElements(By.xpath("//div[@class='modal-columns clearfix']/ul[@class='service__list']/li/a[@class='service__list_link']"));

        for(int i = 0; i < elementsSections.size(); ++i) {
            String nameSections = elementsSections.get(i).findElement(By.xpath(".//span[@class='service__name']")).getText();
            String hrefSections = elementsSections.get(i).getAttribute("href");
            String[] itemSections = {nameSections, hrefSections};
            sections.add(itemSections);
        }
        newCSV = new CsvWriter();
        newCSV.writeCSV(sections);
    }

    public List<Pair<String, String>> GetListLinks(String socialPage) {
        config.getDriver().get(socialPage);
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        List<WebElement> elementsService = this.config.getDriver().findElements(By.xpath("//div/a[attribute::*[contains(local-name(), 'href')]][@class='g-tile-box g-tile-front g-tile--t-1']"));

        for(int i = 0; i < elementsService.size(); ++i) {
            result.add(new Pair<String, String>(elementsService.get(i).getAttribute("href"), elementsService.get(i).getAttribute("title")));
        }
        return result;
    }

    public String VerificationLink(String link){
        String value;
        if (link.contains("egServiceTarget")) {
            config.getDriver().get(link);
            value = this.config.getDriver().findElement(By.xpath("//h3[@class='modal-body-headcrumbs-title']")).getText();
            return value;
        }
        else if (link.contains("egService")) {
            config.getDriver().get(link);
            value = this.config.getDriver().findElement(By.xpath("//div[1]/ul/li[@class='service-link service-block clearfix'][1]/a")).getAttribute("href");
            return VerificationLink(value);
        }
        else {return "";}
    }
    public void closeWindow() {
        this.config.getDriver().quit();
    }

    public String getTitle() {
        return this.config.getDriver().getTitle();
    }

    public void openPage(String page) {
        this.config.getDriver().get(page);
    }


}
