/*
 * Abstract base class for medical procedures.
 * SOLID: Open/Closed principle - we can add new procedure types
 * (e.g., Physiotherapy) without changing existing code.
 */
public abstract class MedicalProcedure {

    protected String name;
    protected String details;
    protected boolean done;

    public MedicalProcedure(String name, String details) {
        this.name = name;
        this.details = details;
        this.done = false;
    }

    public abstract String getType();

    public void markAsDone() {
        this.done = true;
    }

    public boolean isDone() { return done; }
    public String getName() { return name; }
    public String getDetails() { return details; }

    @Override
    public String toString() {
        return getType() + " | " + name + " | " + details
                + " | " + (done ? "DONE" : "PENDING");
    }
}
