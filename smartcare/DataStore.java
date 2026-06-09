import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * SINGLETON PATTERN
 * Reason: The assignment requires "a single point of access" to data and files.
 * Singleton makes sure only ONE DataStore exists in the whole program,
 * so every part of the system shares the same lists and the same file access.
 */
public class DataStore {

    // the only instance
    private static DataStore instance;

    // in-memory data
    private List<Person> people;
    private List<Section> sections;
    private List<MedicalProcedure> procedureCatalog; // available procedure types

    // private constructor so nobody can do "new DataStore()" from outside
    private DataStore() {
        people = new ArrayList<>();
        sections = new ArrayList<>();
        procedureCatalog = new ArrayList<>();
    }

    // single point of access
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // ---- people ----
    public void addPerson(Person p) {
        people.add(p);
    }

    public Person findPersonById(String id) {
        for (Person p : people) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Person> getPeople() {
        return people;
    }

    public List<Patient> getPatients() {
        List<Patient> list = new ArrayList<>();
        for (Person p : people) {
            if (p instanceof Patient) {
                list.add((Patient) p);
            }
        }
        return list;
    }

    // ---- sections / rooms ----
    public void addSection(Section s) {
        sections.add(s);
    }

    public List<Section> getSections() {
        return sections;
    }

    // ---- procedure catalog ----
    public void addProcedureToCatalog(MedicalProcedure p) {
        procedureCatalog.add(p);
    }

    public List<MedicalProcedure> getProcedureCatalog() {
        return procedureCatalog;
    }

    // ---- file I/O (single point of access) ----
    public void saveToFile(String filename, String content) {
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write(content);
            fw.close();
            System.out.println("Saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public String loadFromFile(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return sb.toString();
    }
}
