/*
 * Service interface used by both the real service and the proxy.
 * SOLID: Interface Segregation + Dependency Inversion - callers
 * depend on this interface, not on the concrete implementation.
 */
public interface HospitalService {

    void admitPatient(Patient p, Room r, String start, String end);

    void dischargePatient(Patient p);

    void addProcedureToPatient(Patient p, MedicalProcedure proc);

    void markProcedureDone(Patient p, MedicalProcedure proc);

    void registerPatient(Patient p);

    void addUser(Person user);

    void addSection(Section s);

    void addRoomToSection(Section s, Room r);

    String generateAndSaveReport(String format, String title, String summary, String content);
}
