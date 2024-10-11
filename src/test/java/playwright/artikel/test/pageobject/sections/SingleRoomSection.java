package playwright.artikel.test.pageobject.sections;

import com.microsoft.playwright.Page;

public class SingleRoomSection {
	private final Page page;

	public SingleRoomSection(Page page) {
		this.page = page;
	}

	public boolean clickBookRoom() {
		this.page.locator("css=hotel-room-info").getByText("single").locator("css=btn").getByText("Book this room")
				.click();
		;
		return this.page.locator("css=col-sm-4").isVisible();
	}
}
