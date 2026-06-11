/*
 * PROXY PATTERN (Protection Proxy)
 *
 * The assignment requires role-based access:
 *   - Doctors: admit, discharge, add procedures, view history
 *   - Nurses: register patient, view info, view procedures, mark done
 *   - Admin:  add users, add rooms/sections, generate reports
 *
 * Instead of putting "if (user instanceof Doctor)" checks all over
 * the code, the Proxy wraps the real service and checks the
 * permission of the current user BEFORE forwarding the call.
 *
 * The caller talks to HospitalService and does not care whether
 * the object is the real service or the proxy.
 *
 * SOLID: Single Responsibility - the proxy ONLY does access control,
 * the real service ONLY does the work.
 */
public class HospitalServiceProxy implements HospitalService {

    private RealHospitalService realService;
    private Person currentUser;

    public HospitalServiceProxy(Person currentUser) {
        this.realService = new RealHospitalService();
        this.currentUser = currentUser;
    }

    private boolean denied(String action) {
        System.out.println("ACCESS DENIED: '" + currentUser.getRole()
                + "' cannot perform action: " + action);
        return false;
    }

    @Override
    public void admitPatient(Patient p, Room r, String start, String end) {
        if (currentUser instanceof Doctor) {
            realService.admitPatient(p, r, start, end);
        } else {
            denied("admit patient");
        }
    }

    @Override
    public void dischargePatient(Patient p) {
        if (currentUser instanceof Doctor) {
            realService.dischargePatient(p);
        } else {
            denied("discharge patient");
        }
    }

    @Override
    public void addProcedureToPatient(Patient p, MedicalProcedure proc) {
        if (currentUser instanceof Doctor) {
            realService.addProcedureToPatient(p, proc);
        } else {
            denied("add procedure");
        }
    }

    @Override
    public void markProcedureDone(Patient p, MedicalProcedure proc) {
        // both nurses and doctors are allowed
        if (currentUser instanceof Nurse || currentUser instanceof Doctor) {
            realService.markProcedureDone(p, proc);
        } else {
            denied("mark procedure as done");
        }
    }

    @Override
    public void registerPatient(Patient p) {
        // nurses register patients
        if (currentUser instanceof Nurse || currentUser instanceof Admin) {
            realService.registerPatient(p);
        } else {
            denied("register patient");
        }
    }

    @Override
    public void addUser(Person user) {
        if (currentUser instanceof Admin) {
            realService.addUser(user);
        } else {
            denied("add user");
        }
    }

    @Override
    public void addSection(Section s) {
        if (currentUser instanceof Admin) {
            realService.addSection(s);
        } else {
            denied("add section");
        }
    }

    @Override
    public void addRoomToSection(Section s, Room r) {
        if (currentUser instanceof Admin) {
            realService.addRoomToSection(s, r);
        } else {
            denied("add room");
        }
    }

    @Override
    public String generateAndSaveReport(String format, String title, String summary, String content) {
        if (currentUser instanceof Admin) {
            return realService.generateAndSaveReport(format, title, summary, content);
        } else {
            denied("generate report");
            return null;
        }
    }

    public Person getCurrentUser() { return currentUser; }
}
