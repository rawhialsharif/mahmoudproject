// Concrete subclass in the Template Method pattern
public class XMLReport extends Report {

    public XMLReport(String title, String summary, String content, String adminName) {
        super(title, summary, content, adminName);
    }

    @Override
    protected String formatHeader() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<report>\n"
                + "  <title>" + escape(title) + "</title>\n";
    }

    @Override
    protected String formatSummary() {
        return "  <summary>" + escape(summary) + "</summary>\n";
    }

    @Override
    protected String formatContent() {
        return "  <content>" + escape(content) + "</content>\n";
    }

    @Override
    protected String formatFooter() {
        return "  <generatedBy>" + escape(adminName) + "</generatedBy>\n"
                + "  <date>" + date + "</date>\n"
                + "</report>\n";
    }

    @Override
    public String getFileExtension() {
        return ".xml";
    }

    // small helper to keep xml valid
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
