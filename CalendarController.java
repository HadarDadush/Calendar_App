import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonType;
import java.util.List;

// A class that manages the calendar UI and user interactions.
public class CalendarController {

	final int DAYS_IN_WEEK = 7;
	final int MAX_COL = 6;

	@FXML
	private GridPane calendarGrid;
	@FXML
	private ComboBox<Integer> yearComboBox;
	@FXML
	private ComboBox<Integer> monthComboBox;

	private CalendarLogic calendarLogic;

	public void initialize() {
		calendarLogic = new CalendarLogic();

		// Initialize ComboBoxes and update the calendar after selection.
		if (yearComboBox != null && monthComboBox != null) {

			// Delayed call to update the calendar after ComboBoxes are initialized.
			Platform.runLater(this::updateCalendar);

			// Populate ComboBoxes with years and months.
			populateComboBoxes();

			// Set default values for year and month.
			yearComboBox.getSelectionModel().clearSelection();
			yearComboBox.getSelectionModel().select(0);
			monthComboBox.getSelectionModel().select(0);

			// Add event listeners for ComboBox changes.
			yearComboBox.setOnAction(e -> updateCalendar());
			monthComboBox.setOnAction(e -> updateCalendar());
		}
	}

	// Populate the ComboBoxes with years and months.
	private void populateComboBoxes() {

		// Get the list of years and months from CalendarLogic.
		List<Integer> years = calendarLogic.getYearList();
		List<Integer> months = calendarLogic.getMonthList();

		// Clear previous items to avoid duplicate entries.
		yearComboBox.getItems().clear();
		monthComboBox.getItems().clear();

		// Populate year ComboBox with years from 2020 to 2030.
		yearComboBox.getItems().addAll(years);

		// Set the current year as the selected value.
		yearComboBox.getSelectionModel().select(calendarLogic.getCurrentYear());

		// Populate month ComboBox with months.
		monthComboBox.getItems().addAll(months);

		// Set the current month as the selected value.
		monthComboBox.getSelectionModel().select(calendarLogic.getCurrentMonth() + 1);
	}

	// Update the calendar grid after selecting a year or month.
	public void updateCalendar() {

		// Make sure ComboBox values are not null.
		Integer selectedYear = yearComboBox.getSelectionModel().getSelectedItem();
		Integer selectedMonth = monthComboBox.getSelectionModel().getSelectedItem();

		if (selectedYear == null || selectedMonth == null) {
			return;
		}

		selectedMonth = selectedMonth - 1;

		// Get the list of days for the selected month and year from CalendarLogic.
		List<Integer> days = calendarLogic.getDaysInMonthList(selectedYear, selectedMonth);

		// Get the starting weekday of the month from CalendarLogic.
		int startDay = calendarLogic.getStartDayOfWeek(selectedYear, selectedMonth);

		// Clear existing calendar grid.
		calendarGrid.getChildren().clear();

		// Add the full days of the week as labels in the first row of the grid.
		String[] daysOfWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		for (int i = 0; i < DAYS_IN_WEEK; i++) {
			Label dayOfWeekLabel = new Label(daysOfWeek[i]);
			dayOfWeekLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold; "
					+ "-fx-border-color: black; -fx-background-color: lightgray; "
					+ "-fx-min-width: 90px; -fx-min-height: 20px;");
			calendarGrid.add(dayOfWeekLabel, i, 0);
		}

		// Set the current day labels starting from the second row.
		int row = 1, col = startDay - 1;
		for (int day : days) {
			Label dayLabel = new Label(Integer.toString(day));

			// Retrieve events for the specific year, month, and day.
			List<String> events = calendarLogic.getEvents(selectedYear, selectedMonth, day);
			if (!events.isEmpty()) {
				String eventText = String.join("\n", events);
				dayLabel.setText(day + "\n" + eventText);
			}

			dayLabel.setMinSize(90, 60);
			dayLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-border-color: black;");

			// Add action handler for each day label.
			final int currentDay = day;
			dayLabel.setOnMouseClicked(e -> showDayEvents(currentDay));

			// Place the label in the correct position in the grid.
			calendarGrid.add(dayLabel, col, row);

			col++;
			if (col > MAX_COL) {
				col = 0;
				row++;
			}
		}

	}

	// Show and allow editing of events for a specific day.
	public void showDayEvents(int day) {

		// Get the selected year and month from the ComboBoxes.
		Integer selectedYear = yearComboBox.getSelectionModel().getSelectedItem();
		Integer selectedMonth = monthComboBox.getSelectionModel().getSelectedItem() - 1;

		if (selectedYear == null || selectedMonth == null) {
			return;
		}

		// Retrieve events for the specific date.
		List<String> events = calendarLogic.getEvents(selectedYear, selectedMonth, day);

		// Create a dialog to display and edit events.
		TextArea eventArea = new TextArea();
		eventArea.setText(String.join("\n", events));
		eventArea.setWrapText(true);

		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Edit Events");
		dialog.setHeaderText("Edit events for day " + day);
		dialog.getDialogPane().setContent(eventArea);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				String updatedEvents = eventArea.getText();
				calendarLogic.addEvent(selectedYear, selectedMonth, day, updatedEvents);
				updateCalendar();
			}
			return null;
		});

		dialog.showAndWait();
	}

}