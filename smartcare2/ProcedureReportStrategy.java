public class ProcedureReportStrategy implements ReportStrategy {
    @Override
    public String getTitle() {
        return "Procedures Report";
    }

    @Override
    public String getSummary(DataStore data) {
        int count = 0;
        for (Patient p : data.getPatients()) {
            count += p.getProcedures().size();
        }
        return "Total assigned procedures: " + count;
    }

    @Override
    public String getContent(DataStore data) {
        StringBuilder content = new StringBuilder();
        for (Patient p : data.getPatients()) {
            for (MedicalProcedure m : p.getProcedures()) {
                content.append(p.getName()).append(": ").append(m).append("\n");
            }
        }
        return content.toString();
    }
}
