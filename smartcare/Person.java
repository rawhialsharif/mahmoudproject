/*
 * BRIDGE PATTERN (Abstraction side)
 *
 * Person is the abstraction. It HAS-A Residency (the implementor).
 * Doctor, Nurse, Admin, Patient extend Person but they DO NOT need
 * to know if the person is Jordanian or not. The residency is a
 * separate hierarchy that can vary independently.
 *
 * SOLID: Single Responsibility - Person stores person info,
 * Residency stores residency info. Each class has one reason to change.
 */
public abstract class Person {

    protected String id;
    protected String name;
    protected int age;
    protected Residency residency;   // <-- the bridge

    public Person(String id, String name, int age, Residency residency) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.residency = residency;
    }

    // Each subclass tells what role it plays
    public abstract String getRole();

    public String getInfo() {
        return "[" + getRole() + "] ID: " + id
                + ", Name: " + name
                + ", Age: " + age
                + ", Residency: " + residency.getResidencyType()
                + " (" + residency.getResidencyInfo() + ")";
    }

    // getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public Residency getResidency() { return residency; }
}
