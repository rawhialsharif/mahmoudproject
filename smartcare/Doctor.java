public class Doctor extends Person {

    private String specialty;

    public Doctor(String id, String name, int age, Residency residency) {
        super(id, name, age, residency);
        this.specialty = "General";
    }

    public Doctor(String id, String name, int age, Residency residency, String specialty) {
        super(id, name, age, residency);
        this.specialty = specialty;
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}
