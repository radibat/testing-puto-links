import io.qameta.allure.*;
import io.qameta.allure.model.Status;
//import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import properties.Proper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(OrderAnnotation.class)
public class TestSocialLinks {

    private static Logger log = LogManager.getLogger(TestSocialLinks.class);
    Properties property = new Properties();

    static String ContextURL;

    static {
        try {
            ContextURL = Proper.getProps().getProperty("ContextURL");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static BaseTestLink BaseTestLink;
    private String expectedTitle = "Запрашиваемая вами услуга была удалена из РГУ.";
    private int countLinks = 5;
    private List<Pair<String, String>> links = new ArrayList<Pair<String, String>>();

    @BeforeAll
    static void setUp() throws IOException {
        BaseTestLink = new BaseTestLink(ContextURL);
        BaseTestLink.GetSocialSections( ContextURL + "/social.htm");
        step("Открытие браузера для тестируемого контекста");
        log.info("Opening browser");
    }

    @ParameterizedTest(name = " {index}. Страница {0}, ссылка {1}")
    @CsvFileSource(resources = "/socialLinks.csv")
    @Features(value = {@Feature(value = "Проверка ссылок"), @Feature(value = "Социальное обеспечение")})
    void openRequestForm(String name, String link) {
        links = BaseTestLink.GetListLinks(link); //ContextURL +
        int count = 0;
        Allure.addAttachment(name, "text/uri-list", link); //ContextURL +
        step("Количество ссылок на странице: " + links.size());

        log.info("Page testing started: "  + name);

        for (int i = 0; i < links.size(); i++)
        {
            String href = links.get(i).getL();
            String titleHref = links.get(i).getR();
            String titleService = BaseTestLink.VerificationLink(href);

            if (!titleService.equals(expectedTitle))
            {
                count++;
                step("Корректная ссылка: " + titleHref);
            }
            else {
                log.warn("Incorrect link " + href + " with name " + titleHref + " on page " + name); ;
                step("Битая ссылка: "+ titleHref +  ", " + href, Status.FAILED);
            }
        }
        log.info(count + " out of "  + links.size() + " correct links");
        assertEquals(links.size(), count);
    }


    @AfterAll
    public static void tearDown() {
        BaseTestLink.closeWindow();
        log.info("Closing browser");
        step("Закрытие браузера для тестируемого контекста");
    }
}
