import java.util.ArrayList;
import java.util.List;

public class Section {

    private String name;
    private List<Room> rooms;

    public Section(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room r) {
        rooms.add(r);
    }

    public Room findAvailableRoom() {
        for (Room r : rooms) {
            if (r.isAvailable()) {
                return r;
            }
        }
        return null;
    }

    public Room findRoomByNumber(String number) {
        for (Room r : rooms) {
            if (r.getRoomNumber().equalsIgnoreCase(number)) {
                return r;
            }
        }
        return null;
    }

    public String getName() { return name; }
    public List<Room> getRooms() { return rooms; }
}
