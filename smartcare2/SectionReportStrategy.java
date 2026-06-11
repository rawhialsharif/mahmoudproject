public class SectionReportStrategy implements ReportStrategy {
    @Override
    public String getTitle() {
        return "Sections Report";
    }

    @Override
    public String getSummary(DataStore data) {
        return "Total sections: " + data.getSections().size();
    }

    @Override
    public String getContent(DataStore data) {
        StringBuilder content = new StringBuilder();
        for (Section s : data.getSections()) {
            content.append("- ").append(s.getName())
                   .append(" (").append(s.getRooms().size()).append(" rooms)\n");
        }
        return content.toString();
    }
}
