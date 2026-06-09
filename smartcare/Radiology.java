public class Radiology extends MedicalProcedure {
    public Radiology(String name, String details) {
        super(name, details);
    }
    @Override
    public String getType() { return "Radiology"; }
}
