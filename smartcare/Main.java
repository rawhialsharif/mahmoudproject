import java.util.List;
import java.util.Scanner;

/*
 * Smart Care - Patient Admission System
 *
 * Patterns used:
 *   - Singleton (DataStore)             single point of access for data and files
 *   - Factory   (UserFactory)           creates Patient/Doctor/Nurse/Admin
 *   - Bridge    (Person + Residency)    person hierarchy vs residency hierarchy
 *   - Template  (Report + Text/XML)     fixed report skeleton, different formats
 *   - Proxy     (HospitalServiceProxy)  role-based access control
 *
 * Run:
 *   javac *.java
 *   java Main
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static DataStore data = DataStore.getInstance();

    public static void main(String[] args) {
        seedData();

        System.out.println("==================================================");
        System.out.println("   Welcome to Smart Care - Patient Admission");
        System.out.println("==================================================");

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Login");
            System.out.println("2. List all users (debug)");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            String c = scanner.nextLine().trim();

            if (c.equals("1")) {
                login();
            } else if (c.equals("2")) {
                for (Person p : data.getPeople()) {
                    System.out.println(" - " + p.getInfo());
                }
            } else if (c.equals("0")) {
                System.out.println("Goodbye.");
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // -----------------------------------------------------------
    //  LOGIN
    // -----------------------------------------------------------
    private static void login() {
        System.out.print("Enter your ID: ");
        String id = scanner.nextLine().trim();
        Person user = data.findPersonById(id);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        if (user instanceof Patient) {
            System.out.println("Patients cannot log in.");
            return;
        }
        System.out.println("Welcome " + user.getName() + " (" + user.getRole() + ")");
        // every action goes through the proxy
        HospitalServiceProxy service = new HospitalServiceProxy(user);
        if (user instanceof Doctor) {
            doctorMenu(service);
        } else if (user instanceof Nurse) {
            nurseMenu(service);
        } else if (user instanceof Admin) {
            adminMenu(service);
        }
    }

    // -----------------------------------------------------------
    //  DOCTOR MENU
    // -----------------------------------------------------------
    private static void doctorMenu(HospitalServiceProxy service) {
        while (true) {
            System.out.println("\n--- Doctor Menu ---");
            System.out.println("1. Admit patient");
            System.out.println("2. Discharge patient");
            System.out.println("3. Add medical procedure to patient");
            System.out.println("4. View patient history");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            String c = scanner.nextLine().trim();
            if (c.equals("1")) doAdmit(service);
            else if (c.equals("2")) doDischarge(service);
            else if (c.equals("3")) doAddProcedure(service);
            else if (c.equals("4")) viewPatientHistory();
            else if (c.equals("0")) return;
            else System.out.println("Invalid choice.");
        }
    }

    private static void doAdmit(HospitalServiceProxy service) {
        Patient p = pickPatient();
        if (p == null) return;
        if (p.isAdmitted()) {
            System.out.println("Patient is already admitted.");
            return;
        }
        Section s = pickSection();
        if (s == null) return;
        Room r = s.findAvailableRoom();
        if (r == null) {
            System.out.println("No available room in this section.");
            return;
        }
        System.out.print("Admission start date: ");
        String start = scanner.nextLine().trim();
        System.out.print("Admission end date: ");
        String end = scanner.nextLine().trim();
        service.admitPatient(p, r, start, end);
    }

    private static void doDischarge(HospitalServiceProxy service) {
        Patient p = pickPatient();
        if (p == null) return;
        service.dischargePatient(p);
    }

    private static void doAddProcedure(HospitalServiceProxy service) {
        Patient p = pickPatient();
        if (p == null) return;
        System.out.println("Procedure type:");
        System.out.println("  1. Medication");
        System.out.println("  2. LabTest");
        System.out.println("  3. Radiology");
        System.out.println("  4. Monitoring");
        System.out.print("Choice: ");
        String t = scanner.nextLine().trim();
        System.out.print("Procedure name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Details: ");
        String det = scanner.nextLine().trim();
        MedicalProcedure proc;
        if (t.equals("1")) proc = new Medication(name, det);
        else if (t.equals("2")) proc = new LabTest(name, det);
        else if (t.equals("3")) proc = new Radiology(name, det);
        else if (t.equals("4")) proc = new Monitoring(name, det);
        else { System.out.println("Invalid type."); return; }
        service.addProcedureToPatient(p, proc);
    }

    private static void viewPatientHistory() {
        Patient p = pickPatient();
        if (p == null) return;
        System.out.println("\n" + p.getHistory());
    }

    // -----------------------------------------------------------
    //  NURSE MENU
    // -----------------------------------------------------------
    private static void nurseMenu(HospitalServiceProxy service) {
        while (true) {
            System.out.println("\n--- Nurse Menu ---");
            System.out.println("1. Register new patient");
            System.out.println("2. View patient info / history");
            System.out.println("3. View required procedures of a patient");
            System.out.println("4. Mark procedure as done");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            String c = scanner.nextLine().trim();
            if (c.equals("1")) doRegisterPatient(service);
            else if (c.equals("2")) viewPatientHistory();
            else if (c.equals("3")) viewProcedures();
            else if (c.equals("4")) doMarkDone(service);
            else if (c.equals("0")) return;
            else System.out.println("Invalid choice.");
        }
    }

    private static void doRegisterPatient(HospitalServiceProxy service) {
        System.out.print("New patient ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());
        Residency res = readResidency();
        // FACTORY is used here to create the patient
        Person person = UserFactory.createUser("patient", id, name, age, res);
        service.registerPatient((Patient) person);
    }

    private static void viewProcedures() {
        Patient p = pickPatient();
        if (p == null) return;
        List<MedicalProcedure> list = p.getProcedures();
        if (list.isEmpty()) {
            System.out.println("No procedures for this patient.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + list.get(i));
        }
    }

    private static void doMarkDone(HospitalServiceProxy service) {
        Patient p = pickPatient();
        if (p == null) return;
        List<MedicalProcedure> list = p.getProcedures();
        if (list.isEmpty()) {
            System.out.println("No procedures.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + list.get(i));
        }
        System.out.print("Pick procedure number: ");
        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idx < 0 || idx >= list.size()) {
            System.out.println("Invalid number.");
            return;
        }
        service.markProcedureDone(p, list.get(idx));
    }

    // -----------------------------------------------------------
    //  ADMIN MENU
    // -----------------------------------------------------------
    private static void adminMenu(HospitalServiceProxy service) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add user (doctor/nurse/admin)");
            System.out.println("2. Add section");
            System.out.println("3. Add room to section");
            System.out.println("4. List sections and rooms");
            System.out.println("5. Generate report (text or xml)");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            String c = scanner.nextLine().trim();
            if (c.equals("1")) doAddUser(service);
            else if (c.equals("2")) doAddSection(service);
            else if (c.equals("3")) doAddRoom(service);
            else if (c.equals("4")) listSections();
            else if (c.equals("5")) doGenerateReport(service);
            else if (c.equals("0")) return;
            else System.out.println("Invalid choice.");
        }
    }

    private static void doAddUser(HospitalServiceProxy service) {
        System.out.print("Role (doctor/nurse/admin): ");
        String role = scanner.nextLine().trim();
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());
        Residency res = readResidency();
        // FACTORY again
        Person user = UserFactory.createUser(role, id, name, age, res);
        service.addUser(user);
    }

    private static void doAddSection(HospitalServiceProxy service) {
        System.out.print("Section name: ");
        String n = scanner.nextLine().trim();
        service.addSection(new Section(n));
    }

    private static void doAddRoom(HospitalServiceProxy service) {
        Section s = pickSection();
        if (s == null) return;
        System.out.print("Room number: ");
        String num = scanner.nextLine().trim();
        System.out.print("Type (Private/Shared2/Shared4): ");
        String type = scanner.nextLine().trim();
        service.addRoomToSection(s, new Room(num, type));
    }

    private static void listSections() {
        for (Section s : data.getSections()) {
            System.out.println("Section: " + s.getName());
            for (Room r : s.getRooms()) {
                System.out.println("  " + r);
            }
        }
    }

    private static void doGenerateReport(HospitalServiceProxy service) {
        System.out.println("Report type:");
        System.out.println("  1. Patients");
        System.out.println("  2. Rooms");
        System.out.println("  3. Sections");
        System.out.println("  4. Procedures");
        System.out.print("Choice: ");
        String t = scanner.nextLine().trim();
        System.out.print("Format (text/xml): ");
        String fmt = scanner.nextLine().trim();

        String title;
        String summary;
        StringBuilder content = new StringBuilder();
        if (t.equals("1")) {
            title = "Patients Report";
            List<Patient> ps = data.getPatients();
            summary = "Total patients: " + ps.size();
            for (Patient p : ps) content.append(p.getInfo()).append("\n");
        } else if (t.equals("2")) {
            title = "Rooms Report";
            int total = 0;
            for (Section s : data.getSections()) total += s.getRooms().size();
            summary = "Total rooms: " + total;
            for (Section s : data.getSections())
                for (Room r : s.getRooms())
                    content.append(s.getName()).append(" - ").append(r).append("\n");
        } else if (t.equals("3")) {
            title = "Sections Report";
            summary = "Total sections: " + data.getSections().size();
            for (Section s : data.getSections())
                content.append("- ").append(s.getName())
                       .append(" (").append(s.getRooms().size()).append(" rooms)\n");
        } else if (t.equals("4")) {
            title = "Procedures Report";
            int count = 0;
            for (Patient p : data.getPatients()) count += p.getProcedures().size();
            summary = "Total assigned procedures: " + count;
            for (Patient p : data.getPatients())
                for (MedicalProcedure m : p.getProcedures())
                    content.append(p.getName()).append(": ").append(m).append("\n");
        } else {
            System.out.println("Invalid.");
            return;
        }

        String result = service.generateAndSaveReport(fmt, title, summary, content.toString());
        if (result != null) {
            System.out.println("\n--- Report Preview ---\n" + result);
        }
    }

    // -----------------------------------------------------------
    //  HELPERS
    // -----------------------------------------------------------
    private static Residency readResidency() {
        System.out.print("Is the person Jordanian? (y/n): ");
        String j = scanner.nextLine().trim();
        if (j.equalsIgnoreCase("y")) {
            System.out.print("National number: ");
            String nn = scanner.nextLine().trim();
            System.out.print("ID card number: ");
            String ic = scanner.nextLine().trim();
            return new JordanianResidency(nn, ic);
        } else {
            System.out.print("Residential number: ");
            String rn = scanner.nextLine().trim();
            System.out.print("Nationality: ");
            String nat = scanner.nextLine().trim();
            return new NonJordanianResidency(rn, nat);
        }
    }

    private static Patient pickPatient() {
        List<Patient> ps = data.getPatients();
        if (ps.isEmpty()) {
            System.out.println("No patients in the system.");
            return null;
        }
        for (int i = 0; i < ps.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + ps.get(i).getInfo());
        }
        System.out.print("Pick patient number: ");
        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idx < 0 || idx >= ps.size()) {
            System.out.println("Invalid.");
            return null;
        }
        return ps.get(idx);
    }

    private static Section pickSection() {
        if (data.getSections().isEmpty()) {
            System.out.println("No sections.");
            return null;
        }
        for (int i = 0; i < data.getSections().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + data.getSections().get(i).getName());
        }
        System.out.print("Pick section number: ");
        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (idx < 0 || idx >= data.getSections().size()) {
            System.out.println("Invalid.");
            return null;
        }
        return data.getSections().get(idx);
    }

    // -----------------------------------------------------------
    //  SEED DATA so the system is usable right away
    // -----------------------------------------------------------
    private static void seedData() {
        // users (using FACTORY)
        Person admin = UserFactory.createUser("admin", "A001", "Sara Admin", 35,
                new JordanianResidency("9999999999", "ID-A001"));
        Person doctor = UserFactory.createUser("doctor", "D001", "Dr. Ali", 45,
                new JordanianResidency("1111111111", "ID-D001"));
        Person nurse = UserFactory.createUser("nurse", "N001", "Lina Nurse", 28,
                new NonJordanianResidency("R-1001", "Egyptian"));
        Person patient = UserFactory.createUser("patient", "P001", "Omar Patient", 30,
                new JordanianResidency("2222222222", "ID-P001"));

        data.addPerson(admin);
        data.addPerson(doctor);
        data.addPerson(nurse);
        data.addPerson(patient);

        // sections + rooms
        Section cardio = new Section("Cardiology");
        cardio.addRoom(new Room("101", "Private"));
        cardio.addRoom(new Room("102", "Shared2"));
        Section ortho = new Section("Orthopedics");
        ortho.addRoom(new Room("201", "Shared4"));
        data.addSection(cardio);
        data.addSection(ortho);

        // procedure catalog
        data.addProcedureToCatalog(new Medication("Paracetamol", "500mg every 8h"));
        data.addProcedureToCatalog(new LabTest("CBC", "Complete blood count"));
        data.addProcedureToCatalog(new Radiology("Chest X-ray", "PA view"));
        data.addProcedureToCatalog(new Monitoring("Blood pressure", "every 4h"));

        System.out.println("Seed data loaded.");
        System.out.println("Try logging in as: A001 (admin), D001 (doctor), or N001 (nurse).");
    }
}
