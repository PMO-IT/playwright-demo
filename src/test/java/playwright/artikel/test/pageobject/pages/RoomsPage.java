package playwright.artikel.test.pageobject.pages;

import com.microsoft.playwright.Page;

import playwright.artikel.test.pageobject.sections.SingleRoomSection;

public class RoomsPage {
	private final Page page;
	private final SingleRoomSection singleRoomSection;
	private String url = "https://automationintesting.online/";

	public RoomsPage(Page page) {
		this.page = page;
		this.singleRoomSection = new SingleRoomSection(page);
	}

	public void navigate() {
		page.navigate(url);
	}

	public void clickLetMeHack() {
		page.locator("css=button").getByText("Let me hack!").click();
	}

	public boolean isOnRoomsPage() {
		return page.locator("css=h2").getByText("Rooms").isVisible();
	}

	public boolean clickBookSingleRoom() {
		return this.singleRoomSection.clickBookRoom();
	}

	public boolean reserveRoomInSameMonth(int from, int to, String firstname, String lastname, String email,
			String phone) {

		return this.singleRoomSection.reserveRoomInSameMonth(from, to, firstname, lastname, email, phone);

	}
}
