/*
 * BRIDGE PATTERN (Implementor side)
 *
 * The Person hierarchy (Patient, Doctor, Nurse, Admin) and the Residency
 * hierarchy (Jordanian, NonJordanian) can change independently.
 * Without Bridge we would need 4 x 2 = 8 classes (JordanianDoctor,
 * NonJordanianDoctor, JordanianPatient, ...). The Bridge pattern lets
 * us "bridge" the two hierarchies by giving every Person a Residency
 * reference instead of inheriting it.
 */
public interface Residency {
    String getResidencyType();
    String getResidencyInfo();
}
