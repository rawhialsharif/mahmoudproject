public class Monitoring extends MedicalProcedure {
    public Monitoring(String name, String details) {
        super(name, details);
    }
    @Override
    public String getType() { return "Monitoring"; }
}
