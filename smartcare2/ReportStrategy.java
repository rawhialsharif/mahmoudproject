public interface ReportStrategy {
    String getTitle();
    String getSummary(DataStore data);
    String getContent(DataStore data);
}
