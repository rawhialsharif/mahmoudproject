import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {

    private List<MedicalProcedure> procedures;
    private Room assignedRoom;
    private String admissionStart;
    private String admissionEnd;
    private boolean admitted;

    public Patient(String id, String name, int age, Residency residency) {
        super(id, name, age, residency);
        this.procedures = new ArrayList<>();
        this.admitted = false;
    }

    @Override
    public String getRole() {
        return "Patient";
    }

    public void admit(Room r, String start, String end) {
        this.assignedRoom = r;
        this.admissionStart = start;
        this.admissionEnd = end;
        this.admitted = true;
        r.addPatient(this);
    }

    public void discharge() {
        if (assignedRoom != null) {
            assignedRoom.removePatient(this);
        }
        this.assignedRoom = null;
        this.admitted = false;
    }

    public void extendAdmission(String newEnd) {
        this.admissionEnd = newEnd;
    }

    public void addProcedure(MedicalProcedure p) {
        procedures.add(p);
    }

    public List<MedicalProcedure> getProcedures() {
        return procedures;
    }

    public Room getAssignedRoom() { return assignedRoom; }
    public String getAdmissionStart() { return admissionStart; }
    public String getAdmissionEnd() { return admissionEnd; }
    public boolean isAdmitted() { return admitted; }

    public String getHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append(getInfo()).append("\n");
        if (admitted) {
            sb.append("  Admitted in room ").append(assignedRoom.getRoomNumber())
              .append(" from ").append(admissionStart)
              .append(" to ").append(admissionEnd).append("\n");
        } else {
            sb.append("  Not currently admitted\n");
        }
        sb.append("  Procedures:\n");
        if (procedures.isEmpty()) {
            sb.append("    (none)\n");
        } else {
            for (MedicalProcedure p : procedures) {
                sb.append("    - ").append(p.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
