public class Nurse extends Person {

    public Nurse(String id, String name, int age, Residency residency) {
        super(id, name, age, residency);
    }

    @Override
    public String getRole() {
        return "Nurse";
    }
}
