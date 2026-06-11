public class ReportContext {
    private ReportStrategy strategy;

    public ReportContext(ReportStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ReportStrategy strategy) {
        this.strategy = strategy;
    }

    public String generateReport(HospitalServiceProxy service, DataStore data, String format) {
        String title = strategy.getTitle();
        String summary = strategy.getSummary(data);
        String content = strategy.getContent(data);
        return service.generateAndSaveReport(format, title, summary, content);
    }
}
