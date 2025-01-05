import java.util.HashMap;
import java.util.Map;

// A class responsible for storing and retrieving meetings for specific days.
public class MeetingsStorage {

	private Map<Integer, Meeting> meetings;

	// Constructor to initialize the storage.
	public MeetingsStorage() {
		meetings = new HashMap<>();
	}

	// Method to add a meeting.
	public void addMeeting(int day, Meeting meeting) {
		meetings.put(day, meeting);
	}

	// Method to retrieve the meeting for a specific day.
	public Meeting getMeeting(int day) {
		return meetings.get(day);
	}
}