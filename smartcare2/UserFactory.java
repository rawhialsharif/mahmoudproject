/*
 * FACTORY PATTERN
 *
 * The system creates 4 kinds of users (Patient, Doctor, Nurse, Admin).
 * If we let other classes do "new Doctor(...)" directly, then every
 * place that creates a user needs to know all subclasses. A Factory
 * centralizes user creation in ONE place.
 *
 * SOLID: Open/Closed - to add a new role we only edit the factory,
 * not every place in the system that creates users.
 */
public class UserFactory {

    public static Person createUser(String role,
                                    String id,
                                    String name,
                                    int age,
                                    Residency residency) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        String r = role.trim().toLowerCase();
        if (r.equals("patient")) {
            return new Patient(id, name, age, residency);
        } else if (r.equals("doctor")) {
            return new Doctor(id, name, age, residency);
        } else if (r.equals("nurse")) {
            return new Nurse(id, name, age, residency);
        } else if (r.equals("admin")) {
            return new Admin(id, name, age, residency);
        } else {
            throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
