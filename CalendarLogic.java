import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A class that manages calendar logic, including events and date calculations.
public class CalendarLogic {

	final int NUM_MONTHS = 12;
	final int START_YEAR = 2020;
	final int END_YEAR = 2030;

	private Map<String, List<String>> events;

	public CalendarLogic() {
		events = new HashMap<>();
	}

	// Method to get the number of days in a specific month of a given year.
	public int getDaysInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	// Method to get the starting weekday of a specific month in a given year.
	public int getStartDayOfWeek(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	// Method to get the days in a month for a given year and month.
	public List<Integer> getDaysInMonthList(int year, int month) {
		List<Integer> days = new ArrayList<>();
		int daysInMonth = getDaysInMonth(year, month);
		for (int i = 1; i <= daysInMonth; i++) {
			days.add(i);
		}
		return days;
	}

	// Method to add an event to a specific day.
	public void addEvent(int year, int month, int day, String eventText) {
		String key = year + "-" + (month + 1) + "-" + day;
		events.putIfAbsent(key, new ArrayList<>());
		events.get(key).add(eventText);
	}

	// Method to get the events of a specific day.
	public List<String> getEvents(int year, int month, int day) {
		String key = year + "-" + (month + 1) + "-" + day; // Format the key as "YYYY-MM-DD"
		return events.getOrDefault(key, new ArrayList<>());
	}

	// Method to get the list of years (for ComboBox).
	public List<Integer> getYearList() {
		List<Integer> years = new ArrayList<>();
		for (int i = START_YEAR; i <= END_YEAR; i++) {
			years.add(i);
		}
		return years;
	}

	// Method to get the list of months (for ComboBox).
	public List<Integer> getMonthList() {
		List<Integer> months = new ArrayList<>();
		for (int i = 1; i <= NUM_MONTHS; i++) {
			months.add(i);
		}
		return months;
	}

	// Method to get the current year.
	public int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	// Method to get the current month.
	public int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH);
	}
}