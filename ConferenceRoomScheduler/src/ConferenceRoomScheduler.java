import java.util.*;

class ConferenceRoom {
    String roomName;
    public Map<String, List<TimeSlot>> schedule = new HashMap<>();

    // Constructor
    public ConferenceRoom(String roomName) {
        this.roomName = roomName;
    }

    public void reserveRoom(String date, List<TimeSlot> timeSlots) {
        schedule.put(date, timeSlots);
    }

    public boolean isAvailable(String date, TimeSlot timeSlot) {
        if (schedule.containsKey(date)) {
            for (TimeSlot reservedSlot : schedule.get(date)) {
                if (isOverlap(reservedSlot, timeSlot)) {
                    return false; // Room is not available during the requested time slot
                }
            }
        }
        return true; // Room is available
    }

    public void clearSchedule() {
        schedule.clear();
        System.out.println("Schedule cleared for all dates in room " + roomName);
    }

    private boolean isOverlap(TimeSlot existingSlot, TimeSlot newSlot) {
        // Implement logic to check if two time slots overlap
        // For simplicity, let's assume time slots are in the format "HH:mm"
        // Example logic: (existingStart <= newEnd) and (newStart <= existingEnd)
        return false; // Placeholder, replace with actual logic
    }
}

class TimeSlot {
    String startTime;
    String endTime;

    public TimeSlot(String start, String end) {
        this.startTime = start;
        this.endTime = end;
    }
}

public class ConferenceRoomScheduler {

    static Scanner scanner = new Scanner(System.in);
    static Map<String, ConferenceRoom> conferenceRooms = new HashMap<>();

    public static void main(String[] args) {
        // Create conference rooms
        conferenceRooms.put("room101", new ConferenceRoom("Room101"));
        conferenceRooms.put("room102", new ConferenceRoom("Room102"));

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllRooms();
                    break;

                case 2:
                    reserveRoom();
                    break;

                case 3:
                    checkAvailability();
                    break;

                case 4:
                    viewSchedule();
                    break;

                case 5:
                    clearSchedule();
                    break;

                case 6:
                    System.out.println("Exiting the program.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    static void displayMenu() {
        System.out.println("1. View Room Information");
        System.out.println("2. Reserve Room");
        System.out.println("3. Check Room Availability");
        System.out.println("4. View Room Schedule");
        System.out.println("5. Clear Schedule by room");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    static void viewAllRooms() {
        System.out.println("Available Rooms:");
        System.out.println("+------------------+");
        System.out.println("|   Room Name     |");
        System.out.println("+------------------+");

        for (Map.Entry<String, ConferenceRoom> entry : conferenceRooms.entrySet()) {
            String roomName = entry.getKey();
            System.out.printf("| %-16s |%n", roomName);
        }

        System.out.println("+------------------+");
        System.out.println();
    }

    static void reserveRoom() {
        System.out.print("Enter the room name: ");
        String roomName = scanner.nextLine();

        ConferenceRoom room = conferenceRooms.get(roomName);
        if (room != null) {
            room.reserveRoom(getDateInput(), getTimeSlotsInput());
            scanner.nextLine(); // Consume newline
        } else {
            System.out.println("Room not found.");
        }
    }


    static void checkAvailability() {
        System.out.print("Enter the room name: ");
        String roomName = scanner.nextLine();

        ConferenceRoom room = conferenceRooms.get(roomName);
        if (room != null) {
            System.out.print("Enter date to check availability (YYYY-MM-DD): ");
            String checkDate = scanner.nextLine();

            System.out.print("Enter start time to check availability (HH:mm): ");
            String checkStartTime = scanner.nextLine();

            // Create TimeSlot with only start time
            TimeSlot checkTimeSlot = new TimeSlot(checkStartTime, "");

            // Consume newline character from the buffer
            scanner.nextLine();

            if (room.isAvailable(checkDate, checkTimeSlot)) {
                System.out.println("Room " + roomName + " is available on " + checkDate + " during the requested time");
            } else {
                System.out.println("Room " + roomName + " is not available on " + checkDate + " during the requested time");
            }
        } else {
            System.out.println("Room not found.");
        }
    }



    static void viewSchedule() {
        System.out.println("Schedules for All Rooms:");
        System.out.printf("%-15s%-15s%-15s%-15s%n","Room Name", "Date", "Start Time", "End Time");
        System.out.println("-----------------------------------------------------");

        for (Map.Entry<String, ConferenceRoom> roomEntry : conferenceRooms.entrySet()) {
            String roomName = roomEntry.getKey();
            ConferenceRoom room = roomEntry.getValue();

            for (Map.Entry<String, List<TimeSlot>> entry : room.schedule.entrySet()) {
                String date = entry.getKey();
                List<TimeSlot> timeSlots = entry.getValue();

                for (TimeSlot timeSlot : timeSlots) {
                    System.out.printf("%-15s%-15s%-15s%-15s%n", roomName, date, timeSlot.startTime, timeSlot.endTime);
                }
            }
        }

        System.out.println("-----------------------------------------------------");
    }

    static void clearSchedule() {
        System.out.print("Enter the room name: ");
        String roomName = scanner.nextLine();

        ConferenceRoom room = conferenceRooms.get(roomName);
        if (room != null) {
            room.clearSchedule();
        } else {
            System.out.println("Room not found.");
        }
    }

    static String getDateInput() {
        System.out.print("Enter reservation date (YYYY-MM-DD): ");
        return scanner.nextLine();
    }

    static List<TimeSlot> getTimeSlotsInput() {
        System.out.print("Enter start time (HH:mm): ");
        String startTime = scanner.nextLine();

        System.out.print("Enter end time (HH:mm): ");
        String endTime = scanner.nextLine();

        return Arrays.asList(new TimeSlot(startTime, endTime));
    }
}
