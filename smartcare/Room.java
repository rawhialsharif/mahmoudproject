import java.util.ArrayList;
import java.util.List;

public class Room {

    private String roomNumber;
    private String type;       // "Private", "Shared2", "Shared4"
    private int capacity;
    private List<Patient> patients;

    public Room(String roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        if (type.equalsIgnoreCase("Private")) {
            this.capacity = 1;
        } else if (type.equalsIgnoreCase("Shared2")) {
            this.capacity = 2;
        } else if (type.equalsIgnoreCase("Shared4")) {
            this.capacity = 4;
        } else {
            this.capacity = 1;
        }
        this.patients = new ArrayList<>();
    }

    public boolean isAvailable() {
        return patients.size() < capacity;
    }

    public void addPatient(Patient p) {
        if (isAvailable()) {
            patients.add(p);
        } else {
            System.out.println("Room " + roomNumber + " is full.");
        }
    }

    public void removePatient(Patient p) {
        patients.remove(p);
    }

    public String getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public int getCapacity() { return capacity; }
    public int getOccupancy() { return patients.size(); }
    public List<Patient> getPatients() { return patients; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") "
                + getOccupancy() + "/" + capacity
                + (isAvailable() ? " [AVAILABLE]" : " [FULL]");
    }
}
