public class Admin extends Person {

    public Admin(String id, String name, int age, Residency residency) {
        super(id, name, age, residency);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
