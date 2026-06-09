public class Medication extends MedicalProcedure {
    public Medication(String name, String details) {
        super(name, details);
    }
    @Override
    public String getType() { return "Medication"; }
}
