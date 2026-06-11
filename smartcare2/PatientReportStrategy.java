import java.util.List;

public class PatientReportStrategy implements ReportStrategy {
    @Override
    public String getTitle() {
        return "Patients Report";
    }

    @Override
    public String getSummary(DataStore data) {
        return "Total patients: " + data.getPatients().size();
    }

    @Override
    public String getContent(DataStore data) {
        StringBuilder content = new StringBuilder();
        List<Patient> ps = data.getPatients();
        for (Patient p : ps) {
            content.append(p.getInfo()).append("\n");
        }
        return content.toString();
    }
}
