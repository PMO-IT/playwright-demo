package playwright.artikel.test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;


public class SimpleTest {

    @Test
    public void CheckContactFormSubmit_Successfull() {
        // Playwright initialisieren
        Playwright playwright = Playwright.create();

        // Browser starten
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        // Neuen Browser-Context erstellen
        BrowserContext context = browser.newContext();

        // Neue Seite öffnen
        Page page = context.newPage();

        // Gehe zu Google
        page.navigate("https://automationintesting.online");

        // Kontaktfelder befuellen
        String contactName = "Jane Doe";

        page.fill("input[id='name']", contactName);
        page.fill("input[id='email']", "Jane.Doe@test.de");
        page.fill("input[id='phone']", "+49123456789");
        page.fill("input[id='subject']", "Das ist ein Test");
        page.fill("textarea[id='description']", "Das ist eine Testnachricht für das Kontaktformular");
        page.press("button[id='submitContact']", "Enter");

        // Playwright assert, wartet bis Ergebnis sichbar wird
        String expectedSuccessMsg = "Thanks for getting in touch " + contactName + "!";
        assertThat(page.getByText(expectedSuccessMsg)).isVisible();
    }
}
