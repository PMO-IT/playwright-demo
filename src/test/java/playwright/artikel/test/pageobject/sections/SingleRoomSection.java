package playwright.artikel.test.pageobject.sections;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Mouse.MoveOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;

public class SingleRoomSection {
	private final Page page;
	private Locator monthViewLocator;

	public SingleRoomSection(Page page) {
		this.page = page;
		this.monthViewLocator = page.getByRole(AriaRole.TABLE)
				.filter(new Locator.FilterOptions().setHasText("Sun").setHasText("Mon"));
	}

	public boolean clickBookRoom() {
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Book this room")).click();
		return page.locator(".rbc-calendar").isVisible();
	}

	public boolean reserveRoomInSameMonth(int from, int to, String firstname, String lastname, String email,
			String phone) {
		this.monthViewLocator.click();
		Locator cellFrom = getCellInTable(monthViewLocator, from);
		Locator cellTo = getCellInTable(monthViewLocator, to);
		dragAndDrop(cellFrom, cellTo);
		if (isCorrectDayNumberVisible(from, to)) {

			fillFormandClickSubmit(firstname, lastname, email, phone);
			return isBookingSuccesfullVisible() || page.getByText("The room dates are either").isVisible();

		}

		return false;
	}

	private boolean isBookingSuccesfullVisible() {
		return page.getByRole(AriaRole.DIALOG).getByText("Booking Successful!").isVisible();
	}

	private void fillFormandClickSubmit(String firstname, String lastname, String email, String phone) {
		// page.pause();
		page.getByPlaceholder("Firstname").fill(firstname);
		page.getByPlaceholder("Lastname").fill(lastname);
		page.getByPlaceholder("Email").first().fill(email);
		page.getByPlaceholder("Phone").first().fill(phone);
		page.getByText("Book").first().click();
		assertThat(monthViewLocator).isVisible();
	}

	private void dragAndDrop(Locator from, Locator to) {
		BoundingBox boundFrom = from.boundingBox();
		BoundingBox boundTo = to.boundingBox();
		if (!boundTo.equals(null) && !boundFrom.equals(null)) {
			this.page.mouse().move(boundFrom.x + boundFrom.width / 2, boundFrom.y + boundFrom.height / 2);
			page.mouse().down();
			this.page.mouse().move(boundTo.x + boundTo.width / 2, boundTo.y + boundTo.height / 2,
					new MoveOptions().setSteps(5));
			this.page.mouse().up();

		} else {
			System.out.println("Drag fehlgeschlagen");
		}
	}

	private boolean isCorrectDayNumberVisible(int from, int to) {
		int days = to - from;
		System.out.println(days + " night(s) - £" + days + "00");
		return page.getByText(days + " night(s) - £" + days + "00").first().isVisible();

	}

	private Locator getCellInTable(Locator table, int cellDay) {
		String day = String.format("%02d", cellDay);
		System.out.println(day);
		return table.getByRole(AriaRole.CELL).getByText(day).first();
	}
}
