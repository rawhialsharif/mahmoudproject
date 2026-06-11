import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * TEMPLATE METHOD PATTERN
 *
 * The assignment says every report must follow the same structure:
 *   1) title and summary
 *   2) content
 *   3) date and admin name
 *
 * The order is FIXED (the algorithm), but the FORMATTING differs
 * for text vs xml. Template Method defines the skeleton in
 * generateReport() and lets subclasses override each step.
 *
 * The template method is "final" so subclasses cannot change
 * the order of steps - they only fill in how each step looks.
 */
public abstract class Report {

    protected String title;
    protected String summary;
    protected String content;
    protected String adminName;
    protected String date;

    public Report(String title, String summary, String content, String adminName) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.adminName = adminName;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    // The Template Method - DO NOT OVERRIDE
    public final String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(formatHeader());      // step 1: title
        sb.append(formatSummary());     // step 2: summary
        sb.append(formatContent());     // step 3: content
        sb.append(formatFooter());      // step 4: date + admin name
        return sb.toString();
    }

    // steps that subclasses must implement
    protected abstract String formatHeader();
    protected abstract String formatSummary();
    protected abstract String formatContent();
    protected abstract String formatFooter();

    public abstract String getFileExtension();
}
