// Quick test: prove the Proxy blocks cross-role calls.
public class ProxyTest {
    public static void main(String[] args) {
        DataStore data = DataStore.getInstance();

        Person nurse = UserFactory.createUser("nurse", "N999", "Test Nurse", 25,
                new JordanianResidency("1", "2"));
        Patient patient = (Patient) UserFactory.createUser("patient", "P999", "Test Patient", 40,
                new JordanianResidency("3", "4"));
        data.addPerson(nurse);
        data.addPerson(patient);

        Section s = new Section("TestWard");
        Room r = new Room("R1", "Private");
        s.addRoom(r);
        data.addSection(s);

        System.out.println(">>> Logged in as NURSE; trying admit (should be DENIED)");
        HospitalServiceProxy proxy = new HospitalServiceProxy(nurse);
        proxy.admitPatient(patient, r, "2026-06-06", "2026-06-10");

        System.out.println("\n>>> Same NURSE registers a patient (should be ALLOWED)");
        Patient p2 = (Patient) UserFactory.createUser("patient", "P998", "New One", 22,
                new NonJordanianResidency("R5", "Syrian"));
        proxy.registerPatient(p2);

        System.out.println("\n>>> Switch to DOCTOR; admit (should be ALLOWED)");
        Person doctor = UserFactory.createUser("doctor", "D999", "Test Doc", 50,
                new JordanianResidency("5", "6"));
        HospitalServiceProxy docProxy = new HospitalServiceProxy(doctor);
        docProxy.admitPatient(patient, r, "2026-06-06", "2026-06-10");

        System.out.println("\n>>> DOCTOR tries to generate report (should be DENIED)");
        docProxy.generateAndSaveReport("text", "T", "S", "C");
    }
}
