/*
 * The "real" service that performs the actual operations.
 * The Proxy wraps this and adds access control.
 *
 * SOLID: Single Responsibility - this class only performs operations,
 * it does NOT decide who is allowed to call them.
 */
public class RealHospitalService implements HospitalService {

    private DataStore data = DataStore.getInstance();

    @Override
    public void admitPatient(Patient p, Room r, String start, String end) {
        if (!r.isAvailable()) {
            System.out.println("Cannot admit: room " + r.getRoomNumber() + " is full.");
            return;
        }
        p.admit(r, start, end);
        System.out.println("Patient " + p.getName() + " admitted to room "
                + r.getRoomNumber() + " from " + start + " to " + end + ".");
    }

    @Override
    public void dischargePatient(Patient p) {
        if (!p.isAdmitted()) {
            System.out.println("Patient " + p.getName() + " is not currently admitted.");
            return;
        }
        p.discharge();
        System.out.println("Patient " + p.getName() + " discharged.");
    }

    @Override
    public void addProcedureToPatient(Patient p, MedicalProcedure proc) {
        p.addProcedure(proc);
        System.out.println("Procedure '" + proc.getName() + "' added to "
                + p.getName() + ".");
    }

    @Override
    public void markProcedureDone(Patient p, MedicalProcedure proc) {
        proc.markAsDone();
        System.out.println("Procedure '" + proc.getName() + "' marked as DONE for "
                + p.getName() + ".");
    }

    @Override
    public void registerPatient(Patient p) {
        data.addPerson(p);
        System.out.println("Patient " + p.getName() + " registered.");
    }

    @Override
    public void addUser(Person user) {
        data.addPerson(user);
        System.out.println(user.getRole() + " " + user.getName() + " added to the system.");
    }

    @Override
    public void addSection(Section s) {
        data.addSection(s);
        System.out.println("Section '" + s.getName() + "' added.");
    }

    @Override
    public void addRoomToSection(Section s, Room r) {
        s.addRoom(r);
        System.out.println("Room " + r.getRoomNumber() + " added to section " + s.getName() + ".");
    }

    @Override
    public String generateAndSaveReport(String format, String title, String summary, String content) {
        Report report;
        if (format.equalsIgnoreCase("xml")) {
            report = new XMLReport(title, summary, content, "Admin");
        } else {
            report = new TextReport(title, summary, content, "Admin");
        }
        String body = report.generateReport();
        String filename = "report_" + System.currentTimeMillis() + report.getFileExtension();
        data.saveToFile(filename, body);
        return body;
    }
}
