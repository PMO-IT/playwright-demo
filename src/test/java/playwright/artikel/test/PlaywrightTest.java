package playwright.artikel.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

import playwright.artikel.test.pageobject.pages.RoomsPage;

public class PlaywrightTest {

	private Page page;
	private static Browser browser;
	private static Playwright playwright;

	@BeforeAll
	public static void beforeAll() {
		createBrowser();
	}

	private static void createBrowser() {
		playwright = Playwright.create();
		final BrowserType.LaunchOptions opts = new BrowserType.LaunchOptions();
		opts.setHeadless(false);
		browser = playwright.chromium().launch(opts);
	}

	@Test
	public void easyTest() {
		this.page = browser.newPage();
		page.navigate("https:google.com");
		// page.getByText("Google Suche").isVisible();
		page.getByRole(AriaRole.BUTTON).getByText("Alle akzeptieren").click();
		assertTrue(page.getByRole(AriaRole.BUTTON).getByText("Google Suche").isVisible());
		page.pause();
		PlaywrightAssertions
				.assertThat(page.locator("xpath=//div[@class='FPdoLc lJ9FBc']//center/input[text()='Google Suche']"))
				.isVisible();
		Locator.FilterOptions filter = new Locator.FilterOptions().setHasText("Google Suche");
		assertTrue(page.getByRole(AriaRole.BUTTON).filter(filter).isVisible());

	}

	@Test
	public void easyTestWithPageObject() {
		this.page = browser.newPage();
		RoomsPage roomsPage = new RoomsPage(page);
		roomsPage.navigate();
		roomsPage.clickLetMeHack();
		assertTrue(roomsPage.isOnRoomsPage());
	}

	@Test
	public void hardTestBookRoom() {
		this.page = browser.newPage();
		RoomsPage roomsPage = new RoomsPage(page);
		roomsPage.navigate();
		roomsPage.clickLetMeHack();
		// page.pause();
		boolean isSectionVisible = roomsPage.clickBookSingleRoom();
		assertTrue(isSectionVisible);
		// Book from Day x to Day z
		isSectionVisible = roomsPage.reserveRoomInSameMonth(1, 8, "Hans", "Test", "hansTets@email.com",
				"1234567899999");
		assertTrue(isSectionVisible);

	}
}
