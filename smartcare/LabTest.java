public class LabTest extends MedicalProcedure {
    public LabTest(String name, String details) {
        super(name, details);
    }
    @Override
    public String getType() { return "LabTest"; }
}
