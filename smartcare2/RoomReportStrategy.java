public class RoomReportStrategy implements ReportStrategy {
    @Override
    public String getTitle() {
        return "Rooms Report";
    }

    @Override
    public String getSummary(DataStore data) {
        int total = 0;
        for (Section s : data.getSections()) {
            total += s.getRooms().size();
        }
        return "Total rooms: " + total;
    }

    @Override
    public String getContent(DataStore data) {
        StringBuilder content = new StringBuilder();
        for (Section s : data.getSections()) {
            for (Room r : s.getRooms()) {
                content.append(s.getName()).append(" - ").append(r).append("\n");
            }
        }
        return content.toString();
    }
}
